import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { Observable } from 'rxjs';
import SizeResponse from '../../model/catalog/response/SizeResponse.model';
import SizeRequest from '../../model/catalog/request/SizeRequest.model';

@Injectable({
  providedIn: 'root'
})
export class SizeService {
  
  path= `${GATEWAY_URL}${SERVICES_PATHS.CATALOG}` + '/sizes';
  
  constructor(private http: HttpClient) { }

  getAllSizes() : Observable<SizeResponse[]> {
    return this.http.get<SizeResponse[]>(this.path);
  }
  getSizeById(id: number) : Observable<SizeResponse> {
    return this.http.get<SizeResponse>(`${this.path}/${id}`);
  }
  saveSize(size: SizeRequest) : Observable<SizeResponse> {
    return this.http.post<SizeResponse>(this.path, size);
  }
  updateSize(id: number, size: SizeRequest) : Observable<SizeResponse> {
    return this.http.put<SizeResponse>(`${this.path}/${id}`, size);
  }
  deleteSize(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
