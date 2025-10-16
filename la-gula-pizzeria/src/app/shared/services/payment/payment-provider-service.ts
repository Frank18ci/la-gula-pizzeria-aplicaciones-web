import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { Observable } from 'rxjs';
import PaymentProviderResponse from '../../model/payment/response/paymentProviderResponse.model';
import PaymentProviderRequest from '../../model/payment/request/paymentProviderRequest.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentProviderService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.PAYMENT}` + '/payment-providers';

  constructor(private http: HttpClient) { }

  getAllPaymentProviders() : Observable<PaymentProviderResponse[]> {
    return this.http.get<PaymentProviderResponse[]>(this.path);
  }
  getPaymentProviderById(id: number) : Observable<PaymentProviderResponse> {
    return this.http.get<PaymentProviderResponse>(`${this.path}/${id}`);
  }
  savePaymentProvider(provider: PaymentProviderRequest) : Observable<PaymentProviderResponse> {
    return this.http.post<PaymentProviderResponse>(this.path, provider);
  }
  updatePaymentProvider(id: number, provider: PaymentProviderRequest) : Observable<PaymentProviderResponse> {
    return this.http.put<PaymentProviderResponse>(`${this.path}/${id}`, provider);
  }
  deletePaymentProvider(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
