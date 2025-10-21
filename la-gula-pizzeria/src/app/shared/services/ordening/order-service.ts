import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { Observable } from 'rxjs';
import orderResponse from '../../model/ordening/response/orderResponse.model';
import OrderRequest from '../../model/ordening/request/orderRequest.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.ORDENING}` + '/orders';

  constructor(private http: HttpClient) { }

  getAllOrders() : Observable<orderResponse[]> {
    return this.http.get<orderResponse[]>(this.path);
  }
  getOrderById(id: number) : Observable<orderResponse> {
    return this.http.get<orderResponse>(`${this.path}/${id}`);
  }
  saveOrder(order: OrderRequest) : Observable<orderResponse> {
    return this.http.post<orderResponse>(this.path, null);
  }
  updateOrder(id: number, order: OrderRequest) : Observable<orderResponse> {
    return this.http.put<orderResponse>(`${this.path}/${id}`, order);
  }
  deleteOrder(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }

  getDeliveriesByOrderNumber(orderNumber: string) : Observable<orderResponse[]> {
      return this.http.get<orderResponse[]>(`${this.path}/search?orderNumber=${orderNumber}`);
    }
}
