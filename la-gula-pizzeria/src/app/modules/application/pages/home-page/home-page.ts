import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../shared/modules/material-module.module';
import { Router } from '@angular/router';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';

@Component({
  selector: 'app-home-page',
  imports: [CommonModule, MaterialModule],
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css']
})
export class HomePage  implements OnInit {

  pizza : PizzaResponse[] = []; 


 featuredPizzas = [
    { id: 1, name: 'Margherita', image: 'icons/margherita.jpg', price: 12.99 },
    { id: 2, name: 'Pepperoni', image: 'icons/peperoni.jpg', price: 14.99 },
    { id: 3, name: 'Vegetariana', image: 'icons/vegetariano.jpg', price: 13.49 },
    { id: 4, name: 'Hawaiana', image: 'icons/hawa.jpg', price: 15.00 },
    { id: 5, name: '4 Quesos', image: 'icons/quesos.jpg', price: 16.50 },
    { id: 6, name: 'Mixto', image: 'icons/mixta.jpg', price: 17.25 }
  ];

 constructor(
    private pizzaService: PizzaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadFeaturedPizzas();
  }

  loadFeaturedPizzas() {
    this.pizzaService.getAllPizzas().subscribe({
      error: (err) => console.error(err)
    });
  }

  goToDetails(pizzaId: number) {
    this.router.navigate(['/pizza-details', pizzaId]);
  }






  
  

}
