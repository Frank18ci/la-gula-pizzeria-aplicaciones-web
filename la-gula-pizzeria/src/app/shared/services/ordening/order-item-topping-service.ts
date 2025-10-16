import { Injectable } from '@angular/core';
import OrderItemToppingResponse from '../../model/ordening/response/orderItemToppingResponse.model';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import OrderItemToppingRequest from '../../model/ordening/request/orderItemToppingRequest.model';

@Injectable({
  providedIn: 'root'
})
export class OrderItemToppingService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.ORDENING}` + '/order-item-toppings';

  constructor(private http: HttpClient) { }

  getAllOrderItemToppings() : Observable<OrderItemToppingResponse[]> {
    return this.http.get<OrderItemToppingResponse[]>(this.path);
  }
  getOrderItemToppingById(id: number) : Observable<OrderItemToppingResponse> {
    return this.http.get<OrderItemToppingResponse>(`${this.path}/${id}`);
  }
  saveOrderItemTopping(orderItemTopping: OrderItemToppingRequest) : Observable<OrderItemToppingResponse> {
    return this.http.post<OrderItemToppingResponse>(this.path, orderItemTopping);
  }
  updateOrderItemTopping(id: number, orderItemTopping: OrderItemToppingRequest) : Observable<OrderItemToppingResponse> {
    return this.http.put<OrderItemToppingResponse>(`${this.path}/${id}`, orderItemTopping);
  }
  deleteOrderItemTopping(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
