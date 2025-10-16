import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import RoleResponse from '../../model/user/response/roleResponse.model';
import RoleRequest from '../../model/user/request/roleResquest.model';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.USER}` + '/roles';

  constructor(private http: HttpClient) { }

  getAllRoles() : Observable<RoleResponse[]> {
    return this.http.get<RoleResponse[]>(this.path);
  }
  getRoleById(id: number) : Observable<RoleResponse> {
    return this.http.get<RoleResponse>(`${this.path}/${id}`);
  }
  saveRole(role: RoleRequest) : Observable<RoleResponse> {
    return this.http.post<RoleResponse>(this.path, role);
  }
  updateRole(id: number, role: RoleRequest) : Observable<RoleResponse> {
    return this.http.put<RoleResponse>(`${this.path}/${id}`, role);
  }
  deleteRole(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
