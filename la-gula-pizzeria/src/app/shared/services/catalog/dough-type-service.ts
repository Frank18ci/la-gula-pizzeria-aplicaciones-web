import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import DoughTypeResponse from '../../model/catalog/response/doughTypeResponse.model';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import DoughTypeRequest from '../../model/catalog/request/doughTypeRequest.model';

@Injectable({
  providedIn: 'root'
})
export class DoughTypeService {
  
  path: string = `${GATEWAY_URL}${SERVICES_PATHS.CATALOG}` + '/dough-types';

  constructor(private http: HttpClient) {
  }
  getAllDoughTypes() : Observable<DoughTypeResponse[]> {
    return this.http.get<DoughTypeResponse[]>(this.path);
  }
  getDoughTypeById(id: number) : Observable<DoughTypeResponse> {
    return this.http.get<DoughTypeResponse>(`${this.path}/${id}`);
  }
  saveDoughType(doughType: DoughTypeRequest) : Observable<DoughTypeResponse> {
    return this.http.post<DoughTypeResponse>(this.path, doughType);
  }
  updateDoughType(id: number, doughType: DoughTypeRequest) : Observable<DoughTypeResponse> {
    return this.http.put<DoughTypeResponse>(`${this.path}/${id}`, doughType);
  }
  deleteDoughType(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
