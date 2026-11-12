import { Component, EventEmitter, Output, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../../core/services/auth.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { ChangePasswordRequest } from '../../models/change-password-request';

@Component({
  selector: 'app-change-password-dialog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './change-password-dialog.html',
  styleUrl: './change-password-dialog.css'
})
export class ChangePasswordDialog {
  private authService = inject(AuthService);
  private notification = inject(NotificationService);

  @Output() closed = new EventEmitter<void>();

  passwordData: ChangePasswordRequest = {
    currentPassword: '',
    newPassword: '',
    confirmNewPassword: ''
  };

  loading = false;

  onSubmit(): void {
    if (!this.passwordData.currentPassword || !this.passwordData.newPassword || !this.passwordData.confirmNewPassword) {
      this.notification.error('All fields are required.');
      return;
    }

    if (this.passwordData.newPassword !== this.passwordData.confirmNewPassword) {
      this.notification.error('New password and confirmation do not match.');
      return;
    }

    if (this.passwordData.newPassword.length < 8) {
      this.notification.error('New password must be at least 8 characters.');
      return;
    }

    this.loading = true;
    this.authService.changePassword(this.passwordData).subscribe({
      next: () => {
        this.loading = false;
        this.closed.emit();
        this.notification.success('Password changed successfully! Please log in with your new password.');
        // Automatically logout to clear the invalidated old JWT token
        this.authService.logout();
      },
      error: (err) => {
        this.loading = false;
        const msg = err?.error?.message || err?.error || 'Failed to change password. Please verify your current password.';
        this.notification.error(msg);
      }
    });
  }

  cancel(): void {
    this.closed.emit();
  }
}
