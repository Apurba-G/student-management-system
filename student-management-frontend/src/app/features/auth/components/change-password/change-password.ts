import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { ChangePasswordRequest } from '../../models/change-password-request';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css'
})
export class ChangePassword {
  private authService = inject(AuthService);
  private notification = inject(NotificationService);
  private router = inject(Router);

  passwordData: ChangePasswordRequest = {
    currentPassword: '',
    newPassword: '',
    confirmNewPassword: ''
  };

  loading = false;

  onSubmit(): void {
    if (!this.passwordData.currentPassword || !this.passwordData.newPassword || !this.passwordData.confirmNewPassword) {
      this.notification.error('All password fields are required.');
      return;
    }

    if (this.passwordData.newPassword !== this.passwordData.confirmNewPassword) {
      this.notification.error('New password and confirmation password do not match.');
      return;
    }

    if (this.passwordData.newPassword.length < 8) {
      this.notification.error('New password must contain at least 8 characters.');
      return;
    }

    this.loading = true;
    this.authService.changePassword(this.passwordData).subscribe({
      next: () => {
        this.loading = false;
        this.notification.success('Password updated successfully! Please sign in with your new password.');
        // Automatic logout to clear invalidated old token
        this.authService.logout();
      },
      error: (err) => {
        this.loading = false;
        const msg = err?.error?.message || err?.error || 'Failed to change password. Please verify your current password.';
        this.notification.error(msg);
      }
    });
  }
}
