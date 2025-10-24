import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { CommonModule } from '@angular/common';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import { RootImagePizza } from '../../../../shared/storage/RootImagen';
import { MaterialModule } from '../../../../shared/modules/material-module.module';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-pizzas-page',
  imports: [CommonModule,MaterialModule,FormsModule,RouterModule],
  templateUrl: './pizzas-page.html',
  styleUrl: './pizzas-page.css'
})
export class PizzasPageComponent implements OnInit{
  
   pizzas: PizzaResponse[] = [];
  filteredPizzas: PizzaResponse[] = [];
  rootImagePizza = RootImagePizza;
  
 priceRange: number = 100;
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
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

 // ngOnInit(): void {
  //  this.loadPizza();
 // }

 // loadPizza(){
    // this.pizzaService.getAllPizzas().subscribe({
          //next: (pizzas: PizzaResponse[]) => {
        //    this.pizzas = pizzas;
        //    this.cdr.detectChanges();
      //    },
    //      error: (err) => console.error(err)
   //     });
//}
 ngOnInit() {
    this.pizzaService.getAllPizzas().subscribe({
      next: data => {
        this.pizzas = data;
        this.filteredPizzas = [...this.pizzas];
      }
    });
  }

  filterPizzas() {
    this.filteredPizzas = this.pizzas.filter(pizza =>
      pizza.basePrice <= this.priceRange
    );
  }

  clearFilters() {
    this.priceRange = 100;
    this.sizes.forEach(s => s.selected = false);
    this.doughs.forEach(d => d.selected = false);
    this.filteredPizzas = [...this.pizzas];
  }
}
