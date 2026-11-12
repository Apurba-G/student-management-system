import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { ChangePassword } from './components/change-password/change-password';
import { ForgotPassword } from './components/forgot-password/forgot-password';
import { authGuard } from '../../core/guards/auth.guard';

export const AUTH_ROUTES: Routes = [
  { path: 'login', component: Login },
  { path: 'forgot-password', component: ForgotPassword },
  { path: 'change-password', component: ChangePassword, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
