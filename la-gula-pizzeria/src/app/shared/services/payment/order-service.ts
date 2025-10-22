import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import OrderResponse from '../../model/payment/response/orderResponse.model';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.PAYMENT}` + '/orders';

  constructor(private http: HttpClient) { }

  getAllOrders() : Observable<OrderResponse[]> {
    return this.http.get<OrderResponse[]>(this.path);
  }
  getOrderById(id: number) : Observable<OrderResponse> {
    return this.http.get<OrderResponse>(`${this.path}/${id}`);
  }
  saveOrder(order: OrderResponse) : Observable<OrderResponse> {
    return this.http.post<OrderResponse>(this.path, order);
  }
  updateOrder(id: number, order: OrderResponse) : Observable<OrderResponse> {
    return this.http.put<OrderResponse>(`${this.path}/${id}`, order);
  }
  deleteOrder(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}