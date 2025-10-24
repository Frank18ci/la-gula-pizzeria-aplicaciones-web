import { Component, OnInit } from '@angular/core';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { CommonModule } from '@angular/common';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import { RootImagePizza } from '../../../../shared/storage/RootImagen';
import { MaterialModule } from '../../../../shared/modules/material-module.module';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSliderModule } from '@angular/material/slider';

@Component({
  selector: 'app-pizzas-page',
  standalone: true,
  imports: [CommonModule, MaterialModule, FormsModule, RouterModule,
      MatSliderModule, 
    MatCheckboxModule, 
    MatButtonModule,   
    MatIconModule 
  ],
  templateUrl: './pizzas-page.html',
  styleUrls: ['./pizzas-page.css']
})
export class PizzasPageComponent implements OnInit{
  pizzas: PizzaResponse[] = [];
  filteredPizzas: PizzaResponse[] = [];
  rootImagePizza = RootImagePizza;

  priceRange: number = 150;
  sizes = [
    { name: 'Small', selected: false },
    { name: 'Medium', selected: false },
    { name: 'Large', selected: false }
  ];
  doughs = [
    { name: 'Thin', selected: false },
    { name: 'Thick', selected: false }
  ];

  constructor(
    private pizzaService: PizzaService,
    private router: Router
  ) {}

  ngOnInit() {
    // priceRange is already initialized on the field; ensure pizzas load asynchronously
    this.pizzaService.getAllPizzas().subscribe({
      next: data => {
        this.pizzas = data;
        this.filteredPizzas = [...this.pizzas];
      },
      error: err => console.error(err)
    });
  }

  filterPizzas() {
    this.filteredPizzas = this.pizzas.filter(pizza =>
      pizza.basePrice <= this.priceRange
    );
  }

  onPriceRangeChange(value: number) {
    this.priceRange = value;
    this.filterPizzas();
  }

  clearFilters() {
    this.priceRange = 100;
    this.sizes.forEach(s => s.selected = false);
    this.doughs.forEach(d => d.selected = false);
    this.filteredPizzas = [...this.pizzas];
  }
}
