import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import OrderRequest from '../../model/payment/request/orderRequest.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.PAYMENT}` + '/orders';

  constructor(private http: HttpClient) { }

  getAllOrders() : Observable<OrderRequest[]> {
    return this.http.get<OrderRequest[]>(this.path);
  }
  getOrderById(id: number) : Observable<OrderRequest> {
    return this.http.get<OrderRequest>(`${this.path}/${id}`);
  }
  saveOrder(order: OrderRequest) : Observable<OrderRequest> {
    return this.http.post<OrderRequest>(this.path, order);
  }
  updateOrder(id: number, order: OrderRequest) : Observable<OrderRequest> {
    return this.http.put<OrderRequest>(`${this.path}/${id}`, order);
  }
  deleteOrder(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
