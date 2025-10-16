import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import AdressResponse from '../../model/customer/response/adressResponse.model';
import AdressRequest from '../../model/customer/request/adressRequest.model';

@Injectable({
  providedIn: 'root'
})
export class AdressService {
  
  path = `${GATEWAY_URL}${SERVICES_PATHS.CUSTOMER}` + '/adresses';
  
  constructor(private http: HttpClient) { }

  getAllAdresses() : Observable<AdressResponse[]>{
    return this.http.get<AdressResponse[]>(this.path);
  }
  getAdressById(id: number) : Observable<AdressResponse> {
    return this.http.get<AdressResponse>(`${this.path}/${id}`);
  }
  saveAdress(adress: AdressRequest) : Observable<AdressResponse> {
    return this.http.post<AdressResponse>(this.path, adress);
  }
  updateAdress(id: number, adress: AdressRequest) : Observable<AdressResponse> {
    return this.http.put<AdressResponse>(`${this.path}/${id}`, adress);
  }
  deleteAdress(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
