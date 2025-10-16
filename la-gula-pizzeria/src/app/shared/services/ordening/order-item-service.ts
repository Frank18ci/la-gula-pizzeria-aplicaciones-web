import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { Observable } from 'rxjs';
import OrderItemResponse from '../../model/ordening/response/orderItemResponse.model';
import OrderItemRequest from '../../model/ordening/request/orderItemRequest.model';

@Injectable({
  providedIn: 'root'
})
export class OrderItemService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.ORDENING}` + '/order-items';

  constructor(private http: HttpClient) { }

  getAllOrderItems() : Observable<OrderItemResponse[]> {
    return this.http.get<OrderItemResponse[]>(this.path);
  }
  getOrderItemById(id: number) : Observable<OrderItemResponse> {
    return this.http.get<OrderItemResponse>(`${this.path}/${id}`);
  }
  saveOrderItem(orderItem: OrderItemRequest) : Observable<OrderItemResponse> {
    return this.http.post<OrderItemResponse>(this.path, orderItem);
  }
  updateOrderItem(id: number, orderItem: OrderItemRequest) : Observable<OrderItemResponse> {
    return this.http.put<OrderItemResponse>(`${this.path}/${id}`, orderItem);
  }
  deleteOrderItem(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
