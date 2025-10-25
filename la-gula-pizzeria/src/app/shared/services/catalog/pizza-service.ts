import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import PizzaResponse from '../../model/catalog/response/pizzaResponse.model';
import { GATEWAY_URL, SERVICES_PATHS } from '../config/config';

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
  getPizzaByName(name: string) : Observable<PizzaResponse[]> {
    return this.http.get<PizzaResponse[]>(`${this.path}/search`, { params: { name } });
  }
  getPizzaByPriceRangeAndSizeIdAndDough(minPrice: number, maxPrice: number, sizeId: number = 0, toppingId: number = 0) : Observable<PizzaResponse[]> {
    return this.http.get<PizzaResponse[]>(`${this.path}/searchOptions`, { params: { minPrice, maxPrice, sizeId, toppingId } });
  }
  getPizzaById(id: number) : Observable<PizzaResponse> {
    return this.http.get<PizzaResponse>(`${this.path}/${id}`);
  }
  savePizza(pizza: FormData) : Observable<PizzaResponse> {
    return this.http.post<PizzaResponse>(this.path, pizza);
  }
  updatePizza(id: number, pizza: FormData) : Observable<PizzaResponse> {
    return this.http.put<PizzaResponse>(`${this.path}/${id}`, pizza);
  }
  deletePizza(id: number) : Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
