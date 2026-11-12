import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { ResetPasswordRequest } from '../../models/reset-password-request';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.css'
})
export class ForgotPassword {
  private authService = inject(AuthService);
  private router = inject(Router);
  private notification = inject(NotificationService);

  form = {
    username: '',
    recoveryKey: '',
    newPassword: '',
    confirmPassword: ''
  };

  loading = false;
  errorMsg = '';
  successMsg = '';

  onSubmit(): void {
    this.errorMsg = '';
    this.successMsg = '';

    if (!this.form.username || !this.form.recoveryKey || !this.form.newPassword) {
      this.errorMsg = 'All fields are required.';
      return;
    }

    if (this.form.newPassword.length < 8) {
      this.errorMsg = 'New password must be at least 8 characters long.';
      return;
    }

    if (this.form.newPassword !== this.form.confirmPassword) {
      this.errorMsg = 'New password and confirmation do not match.';
      return;
    }

    const request: ResetPasswordRequest = {
      username: this.form.username.trim(),
      recoveryKey: this.form.recoveryKey.trim(),
      newPassword: this.form.newPassword
    };

    this.loading = true;
    this.authService.resetPassword(request).subscribe({
      next: () => {
        this.loading = false;
        this.successMsg = 'Password reset successfully! Redirecting to login...';
        this.notification.success('Password reset successfully! Please sign in with your new password.');
        setTimeout(() => {
          this.router.navigate(['/auth/login']);
        }, 1500);
      },
      error: (err) => {
        this.loading = false;
        const msg = err?.error?.message || 'Password reset failed. Please check your recovery key and username.';
        this.errorMsg = msg;
        this.notification.error(msg);
      }
    });
  }
}
