import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import DeliveryRequest from '../../model/delivery/request/deliveryRequest.model';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.DELIVERY}` + '/deliveries';

  constructor(private http: HttpClient) { }

  getAllDeliveries() : Observable<DeliveryService[]> {
    return this.http.get<DeliveryService[]>(this.path);
  }
  getDeliveryById(id: number) : Observable<DeliveryService> {
    return this.http.get<DeliveryService>(`${this.path}/${id}`);
  }
  saveDelivery(delivery: DeliveryRequest) : Observable<DeliveryService> {
    return this.http.post<DeliveryService>(this.path, delivery);
  }
  updateDelivery(id: number, delivery: DeliveryRequest) : Observable<DeliveryService> {
    return this.http.put<DeliveryService>(`${this.path}/${id}`, delivery);
  }
  deleteDelivery(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
