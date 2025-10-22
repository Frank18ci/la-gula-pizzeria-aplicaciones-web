import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import DeliveryRequest from '../../model/delivery/request/deliveryRequest.model';
import DeliveryResponse from '../../model/delivery/response/deliveryResponse.model';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.DELIVERY}` + '/deliveries';

  constructor(private http: HttpClient) { }

  getAllDeliveries() : Observable<DeliveryResponse[]> {
    return this.http.get<DeliveryResponse[]>(this.path);
  }
  getDeliveryById(id: number) : Observable<DeliveryResponse> {
    return this.http.get<DeliveryResponse>(`${this.path}/${id}`);
  }
  saveDelivery(delivery: DeliveryRequest) : Observable<DeliveryResponse> {
    return this.http.post<DeliveryResponse>(this.path, delivery);
  }
  updateDelivery(id: number, delivery: DeliveryRequest) : Observable<DeliveryResponse> {
    return this.http.put<DeliveryResponse>(`${this.path}/${id}`, delivery);
  }
  deleteDelivery(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
  getDeliveriesByDriverName(driverName: string) : Observable<DeliveryResponse[]> {
    return this.http.get<DeliveryResponse[]>(`${this.path}/search?driverName=${driverName}`);
  }

}
