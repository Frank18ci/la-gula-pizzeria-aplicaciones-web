import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import ToppingResponse from '../../model/catalog/response/toppingResponse.model';
import ToppingRequest from '../../model/catalog/request/toppingRequest.model';

@Injectable({
  providedIn: 'root'
})
export class ToppingService {
  path = `${GATEWAY_URL}${SERVICES_PATHS.CATALOG}` + '/toppings';

  constructor(private http: HttpClient) { }
  
  getAllToppings() : Observable<ToppingResponse[]> {
    return this.http.get<ToppingResponse[]>(this.path);
  }
  getToppingById(id: number) : Observable<ToppingResponse> {
    return this.http.get<ToppingResponse>(`${this.path}/${id}`);
  }
  saveTopping(topping: FormData) : Observable<ToppingResponse> {
    return this.http.post<ToppingResponse>(this.path, topping);
  }
  updateTopping(id: number, topping: FormData) : Observable<ToppingResponse> {
    return this.http.put<ToppingResponse>(`${this.path}/${id}`, topping);
  }
  deleteTopping(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
