import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../shared/modules/material-module.module';
import { Router } from '@angular/router';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { RootImagePizza } from '../../../../shared/storage/RootImagen';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-home-page',
  imports: [CommonModule, MaterialModule, FormsModule],
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css']
})
export class HomePage  implements OnInit {
  
  rootImagePizza = RootImagePizza;
  pizzas : PizzaResponse[] = []; 

 constructor(
    private pizzaService: PizzaService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadFeaturedPizzas();
  }

  loadFeaturedPizzas() {
    this.pizzaService.getAllPizzas().subscribe({
      next: (pizzas: PizzaResponse[]) => {
        this.pizzas = pizzas;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  goToDetails(pizzaId: number) {
    this.router.navigate(['/application/pizza-details', pizzaId]);
  }






  
  

}
