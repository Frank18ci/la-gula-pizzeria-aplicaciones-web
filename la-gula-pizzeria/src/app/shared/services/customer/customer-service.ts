import { Injectable } from '@angular/core';
import CustomerResponse from '../../model/customer/response/customerResponse.model';
import { Observable } from 'rxjs';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import CustomerRequest from '../../model/customer/request/customerRequest.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  
  path = `${GATEWAY_URL}${SERVICES_PATHS.CUSTOMER}` + '/customers';

  constructor(private http: HttpClient) { }

  getAllCustomers() : Observable<CustomerResponse[]> {
    return this.http.get<CustomerResponse[]>(this.path);
  }
  getCustomerById(id: number) : Observable<CustomerResponse> {
    return this.http.get<CustomerResponse>(`${this.path}/${id}`);
  }
  saveCustomer(customer: CustomerRequest) : Observable<CustomerResponse> {
    return this.http.post<CustomerResponse>(this.path, customer);
  }
  updateCustomer(id: number, customer: CustomerRequest) : Observable<CustomerResponse> {
    return this.http.put<CustomerResponse>(`${this.path}/${id}`, customer);
  }
  deleteCustomer(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
