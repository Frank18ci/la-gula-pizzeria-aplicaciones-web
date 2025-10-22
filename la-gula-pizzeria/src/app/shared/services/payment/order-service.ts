import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import OrderPaymentResponse from '../../model/payment/response/orderPaymentResponse.model';
import OrderPaymentRequest from '../../model/payment/request/orderPaymentRequest.model';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.PAYMENT}` + '/orders';

  constructor(private http: HttpClient) { }

  getAllOrders() : Observable<OrderPaymentResponse[]> {
    return this.http.get<OrderPaymentResponse[]>(this.path);
  }
  getOrderById(id: number) : Observable<OrderPaymentResponse> {
    return this.http.get<OrderPaymentResponse>(`${this.path}/${id}`);
  }
  saveOrder(order: OrderPaymentRequest) : Observable<OrderPaymentResponse> {
    return this.http.post<OrderPaymentResponse>(this.path, order);
  }
  updateOrder(id: number, order: OrderPaymentRequest) : Observable<OrderPaymentResponse> {
    return this.http.put<OrderPaymentResponse>(`${this.path}/${id}`, order);
  }
  deleteOrder(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}