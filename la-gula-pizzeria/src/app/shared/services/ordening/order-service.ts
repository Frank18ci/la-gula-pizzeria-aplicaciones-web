import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { Observable } from 'rxjs';
import OrderResponse from '../../model/ordening/response/orderResponse.model';
import OrderRequest from '../../model/ordening/request/orderRequest.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.ORDENING}` + '/orders';

  constructor(private http: HttpClient) { }

  getAllOrders() : Observable<OrderResponse[]> {
    return this.http.get<OrderResponse[]>(this.path);
  }
  getOrderById(id: number) : Observable<OrderResponse> {
    return this.http.get<OrderResponse>(`${this.path}/${id}`);
  }
  saveOrder(order: OrderRequest) : Observable<OrderResponse> {
    return this.http.post<OrderResponse>(this.path, null);
  }
  updateOrder(id: number, order: OrderRequest) : Observable<OrderResponse> {
    return this.http.put<OrderResponse>(`${this.path}/${id}`, order);
  }
  deleteOrder(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
