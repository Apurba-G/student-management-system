import { Injectable, signal, computed, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LoginRequest } from '../../features/auth/models/login-request';
import { ChangePasswordRequest } from '../../features/auth/models/change-password-request';

export interface UserSession {
  username: string;
  token: string;
  role?: string;
  adminId?: number;
}

export interface LoginResponse {
  token: string;
  tokenType: string;
  adminId: number;
  username: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  private userSignal = signal<UserSession | null>(null);
  readonly currentUser = computed(() => this.userSignal());
  readonly isAuthenticated = computed(() => !!this.userSignal());

  constructor() {
    if (typeof window !== 'undefined' && window.localStorage) {
      const stored = localStorage.getItem('sms_user');
      if (stored) {
        try {
          this.userSignal.set(JSON.parse(stored));
        } catch {
          localStorage.removeItem('sms_user');
        }
      }
    }
  }

  login(credentials: LoginRequest): Observable<UserSession> {
    return this.http.post<LoginResponse>('/api/auth/login', credentials).pipe(
      map(response => {
        const session: UserSession = {
          username: response.username,
          token: response.token,
          role: response.role,
          adminId: response.adminId
        };
        this.userSignal.set(session);
        if (typeof window !== 'undefined' && window.localStorage) {
          localStorage.setItem('sms_user', JSON.stringify(session));
        }
        return session;
      })
    );
  }

  changePassword(request: ChangePasswordRequest): Observable<void> {
    return this.http.post<void>('/api/auth/change-password', request);
  }

  logout(): void {
    this.userSignal.set(null);
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.removeItem('sms_user');
    }
    this.router.navigate(['/auth/login']);
  }
}
