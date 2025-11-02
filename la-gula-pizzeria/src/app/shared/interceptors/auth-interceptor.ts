import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import AuthResponse from '../model/auth/AuthResponse.model';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const cookieService = inject(CookieService);
  const router = inject(Router);
  const user: AuthResponse = JSON.parse(cookieService.get('user') || '{}');
  if (user?.token) {
    req = req.clone({
      setHeaders: { authorization: `Bearer ${user.token}` }
    })
  }

  return next(req).pipe(
    catchError((error) => {
      if (error.status === 403) {
        cookieService.delete('user');
        router.navigate(['/auth/login']);
      }
      return throwError(() => error);
    })
  );
};
