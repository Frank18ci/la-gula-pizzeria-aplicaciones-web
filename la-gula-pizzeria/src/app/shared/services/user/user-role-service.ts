import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import UserRoleResponse from '../../model/user/response/userRoleResponse.model';
import UserRoleRequest from '../../model/user/request/userRoleRequest.model';

@Injectable({
  providedIn: 'root'
})
export class UserRoleService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.USER}` + '/user-roles';

  constructor(private http: HttpClient) { }

  getAllUserRoles() : Observable<UserRoleResponse[]> {
    return this.http.get<UserRoleResponse[]>(this.path);
  }
  getAllUserRolesByUserId(userId: number) : Observable<UserRoleResponse[]> {
    return this.http.get<UserRoleResponse[]>(`${this.path}/user/${userId}`);
  }
  getUserRoleById(id: number) : Observable<UserRoleResponse> {
    return this.http.get<UserRoleResponse>(`${this.path}/${id}`);
  }
  saveUserRole(role: UserRoleRequest) : Observable<UserRoleResponse> {
    return this.http.post<UserRoleResponse>(this.path, role);
  }
  deleteUserRole(userId: number, roleId: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${userId}/${roleId}`);
  }
}
