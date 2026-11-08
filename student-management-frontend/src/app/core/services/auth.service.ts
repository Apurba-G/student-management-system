import { Injectable, signal, computed } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { LoginRequest } from '../../features/auth/models/login-request';

export interface UserSession {
  username: string;
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userSignal = signal<UserSession | null>(null);
  readonly currentUser = computed(() => this.userSignal());
  readonly isAuthenticated = computed(() => !!this.userSignal());

  constructor(private router: Router) {
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

  login(credentials: LoginRequest): Observable<boolean> {
    const session: UserSession = {
      username: credentials.username || 'admin',
      token: 'jwt-sms-dummy-token-' + Date.now()
    };
    this.userSignal.set(session);
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.setItem('sms_user', JSON.stringify(session));
    }
    return of(true);
  }

  logout(): void {
    this.userSignal.set(null);
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.removeItem('sms_user');
    }
    this.router.navigate(['/auth/login']);
  }
}
