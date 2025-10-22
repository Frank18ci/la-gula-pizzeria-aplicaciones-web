import { Component, OnInit } from '@angular/core';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { CommonModule } from '@angular/common';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';

@Component({
  selector: 'app-pizzas-page',
  imports: [CommonModule],
  templateUrl: './pizzas-page.html',
  styleUrl: './pizzas-page.css'
})
export class PizzasPage implements OnInit{
  pizzas: PizzaResponse[] = [];
  constructor(
    private pizzaService: PizzaService
  ) { }
  ngOnInit(): void {
    this.pizzaService.getAllPizzas().subscribe((data) => {
      console.log(data);
      this.pizzas = data;
    });
  }

}
