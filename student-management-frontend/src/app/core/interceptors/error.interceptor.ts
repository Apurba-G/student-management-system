import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../services/notification.service';
import { AuthService } from '../services/auth.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const notificationService = inject(NotificationService);
  const authService = inject(AuthService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'An error occurred';
      if (error.error && error.error.message) {
        errorMessage = error.error.message;
      } else if (error.error && typeof error.error === 'string') {
        errorMessage = error.error;
      } else if (error.statusText) {
        errorMessage = error.status + ': ' + error.statusText;
      }

      if ((error.status === 401 || error.status === 403) && !req.url.includes('/api/auth/login')) {
        authService.logout();
        errorMessage = 'Session expired or unauthorized. Please sign in.';
      }

      notificationService.error(errorMessage);
      return throwError(() => error);
    })
  );
};
