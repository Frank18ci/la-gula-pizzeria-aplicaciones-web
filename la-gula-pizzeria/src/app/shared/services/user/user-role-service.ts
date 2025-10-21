import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import UserRoleResponse from '../../model/user/response/userRoleResponse.model';

@Injectable({
  providedIn: 'root'
})
export class UserRoleService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.USER}` + '/user-roles';

  constructor(private http: HttpClient) { }

  getAllUserRoles() : Observable<UserRoleResponse[]> {
    return this.http.get<UserRoleResponse[]>(this.path);
  }
  getUserRoleById(id: number) : Observable<UserRoleResponse> {
    return this.http.get<UserRoleResponse>(`${this.path}/${id}`);
  }
  saveUserRole(role: UserRoleResponse) : Observable<UserRoleResponse> {
    return this.http.post<UserRoleResponse>(this.path, role);
  }
  deleteUserRole(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
