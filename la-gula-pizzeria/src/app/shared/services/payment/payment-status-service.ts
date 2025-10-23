import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import PaymentStatusResponse from '../../model/payment/response/paymentStatusResponse.model';
import PaymentStatusRequest from '../../model/payment/request/paymentStatusRequest.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentStatusService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.PAYMENT}` + '/payments-status';

  constructor(private http: HttpClient) { }

  getAllPaymentStatuses() : Observable<PaymentStatusResponse[]> {
    return this.http.get<PaymentStatusResponse[]>(this.path);
  }
  getPaymentStatusById(id: number) : Observable<PaymentStatusResponse> {
    return this.http.get<PaymentStatusResponse>(`${this.path}/${id}`);
  }
  savePaymentStatus(status: PaymentStatusRequest) : Observable<PaymentStatusResponse> {
    return this.http.post<PaymentStatusResponse>(this.path, status);
  }
  updatePaymentStatus(id: number, status: PaymentStatusRequest) : Observable<PaymentStatusResponse> {
    return this.http.put<PaymentStatusResponse>(`${this.path}/${id}`, status);
  }
  deletePaymentStatus(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
