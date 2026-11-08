import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../services/notification.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const notificationService = inject(NotificationService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'An error occurred';
      if (error.error && error.error.message) {
        errorMessage = error.error.message;
      } else if (error.statusText) {
        errorMessage = error.status + ': ' + error.statusText;
      }
      notificationService.error(errorMessage);
      return throwError(() => error);
    })
  );
};
