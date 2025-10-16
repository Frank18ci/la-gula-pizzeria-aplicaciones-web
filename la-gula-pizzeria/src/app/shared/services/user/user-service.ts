import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import UserResponse from '../../model/user/response/userResponse.model';
import UserRequest from '../../model/user/request/userRequest.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.USER}` + '/users';

  constructor(private http: HttpClient) { }

  getAllUsers() : Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(this.path);
  }
  getUserById(id: number) : Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.path}/${id}`);
  }
  saveUser(user: UserRequest) : Observable<UserResponse> {
    return this.http.post<UserResponse>(this.path, user);
  }
  updateUser(id: number, user: UserRequest) : Observable<UserResponse> {
    return this.http.put<UserResponse>(`${this.path}/${id}`, user);
  }
  deleteUser(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
