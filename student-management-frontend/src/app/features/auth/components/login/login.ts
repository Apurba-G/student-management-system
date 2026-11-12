import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../core/services/auth.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoginRequest } from '../../models/login-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  private authService = inject(AuthService);
  private router = inject(Router);
  private notification = inject(NotificationService);

  credentials: LoginRequest = {
    username: '',
    password: ''
  };

  loading = false;

  onLogin(): void {
    if (!this.credentials.username || !this.credentials.password) {
      this.notification.error('Please enter both username and password.');
      return;
    }
    this.loading = true;
    this.authService.login(this.credentials).subscribe({
      next: () => {
        this.loading = false;
        this.notification.success('Logged in successfully!');
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.loading = false;
        const msg = err?.error?.message || 'Invalid username or password';
        this.notification.error(msg);
      }
    });
  }
}
