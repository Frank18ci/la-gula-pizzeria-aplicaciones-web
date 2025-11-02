import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import AuthResponse from '../model/auth/AuthResponse.model';

export const adminGuard: CanActivateFn = (route, state) => {
  const cookieService = inject(CookieService)
  const router = inject(Router)
  const user : AuthResponse = JSON.parse(cookieService.get('user') || '{}');
  console.log(user);
  if(user?.token && user?.roles.includes('admin')){
    return true;
  }
  router.navigate(['auth/login'])
  return false;
};
