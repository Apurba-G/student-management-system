import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { ChangePassword } from './components/change-password/change-password';
import { authGuard } from '../../core/guards/auth.guard';

export const AUTH_ROUTES: Routes = [
  { path: 'login', component: Login },
  { path: 'change-password', component: ChangePassword, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
