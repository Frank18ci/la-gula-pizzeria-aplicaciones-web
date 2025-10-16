import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';
import { Observable } from 'rxjs';
import PizzaResponse from '../../model/catalog/response/pizzaResponse.model';
import PizzaRequest from '../../model/catalog/request/pizzaRequest.model';

@Injectable({
  providedIn: 'root'
})
export class PizzaService {
  
  path = `${GATEWAY_URL}${SERVICES_PATHS.CATALOG}` + '/pizzas';
  
  constructor(private http: HttpClient) {
  }
  
  getAllPizzas() : Observable<PizzaResponse[]> {
    return this.http.get<PizzaResponse[]>(this.path);
  }
  getPizzaById(id: number) : Observable<PizzaResponse> {
    return this.http.get<PizzaResponse>(`${this.path}/${id}`);
  }
  savePizza(pizza: PizzaRequest) : Observable<PizzaResponse> {
    return this.http.post<PizzaResponse>(this.path, pizza);
  }
  updatePizza(id: number, pizza: PizzaRequest) : Observable<PizzaResponse> {
    return this.http.put<PizzaResponse>(`${this.path}/${id}`, pizza);
  }
  deletePizza(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
