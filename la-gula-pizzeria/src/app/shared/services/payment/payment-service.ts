import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.PAYMENT}` + '/payments';

  constructor(private http: HttpClient) { }

  getAllPayments() : Observable<PaymentResponse[]> {
    return this.http.get<PaymentResponse[]>(this.path);
  }
  getPaymentById(id: number) : Observable<PaymentResponse> {
    return this.http.get<PaymentResponse>(`${this.path}/${id}`);
  }
  savePayment(payment: PaymentRequest) : Observable<PaymentResponse> {
    return this.http.post<PaymentResponse>(this.path, payment);
  }
  updatePayment(id: number, payment: PaymentRequest) : Observable<PaymentResponse> {
    return this.http.put<PaymentResponse>(`${this.path}/${id}`, payment);
  }
  deletePayment(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
