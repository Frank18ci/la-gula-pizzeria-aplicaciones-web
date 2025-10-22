import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import AuthResponse from '../../model/auth/AuthResponse.model';
import AuthRequest from '../../model/auth/AuthRequest.model';
import UserRequest from '../../model/user/request/userRequest.model';
import UserResponse from '../../model/user/response/userResponse.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  path: string = `${GATEWAY_URL}${SERVICES_PATHS.SECURITY}` + '/auth';

  constructor(private http: HttpClient) {
  }
  login(authRequest: AuthRequest) : Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.path}` + '/login', authRequest);
  }
  register(userRequest: UserRequest) : Observable<UserResponse> {
    return this.http.post<UserResponse>(`${this.path}` + '/register', userRequest);
  }
}
