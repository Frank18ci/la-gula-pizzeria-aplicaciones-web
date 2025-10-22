import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { SizeService } from '../../../../shared/services/catalog/size-service';
import { DoughTypeService } from '../../../../shared/services/catalog/dough-type-service';
import { ToppingService } from '../../../../shared/services/catalog/topping-service';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import { MaterialModule } from '../../../../shared/modules/material-module.module';
import { CommonModule } from '@angular/common';


interface CheeseOption {
  name: string;
  extraPrice: number;
}

@Component({
  selector: 'app-details-pizza',
  imports: [CommonModule,MaterialModule],
  templateUrl: './details-pizza.html',
  styleUrls: ['./details-pizza.css']
})
export class DetailsPizzaComponent  implements OnInit {
  
    pizza! : PizzaResponse;
    pizzaId!: number;
    sizes: any[] = [];
    doughs: any[] = [];
    toppings: any[] = [];
    

  cheeses: CheeseOption[] = [
      { name: 'Mozzarella', extraPrice: 0 },
      { name: 'Cheddar', extraPrice: 1.5 },
      { name: 'Parmesan', extraPrice: 2 }
  ];

  selectedSize!: any;
  selectedDough!: any;
  selectedCheese!: CheeseOption;
  selectedToppings: any[] = [];

  totalPrice: number = 0;

  constructor(
    private route: ActivatedRoute,
    private pizzaService: PizzaService,
    private sizeService: SizeService,
    private doughService: DoughTypeService,
    private toppingService: ToppingService,
    private router: Router
  ){}


  ngOnInit(): void {
    const pizzaId = this .route.snapshot.params['id'];
    
    this.pizzaService.getPizzaById(pizzaId).subscribe({
      next: data => {
        this.pizza = data;
        this.selectedToppings = [...(data.toppings || [])];
        this.updatePrice();
      }
    });

    this.sizeService.getAllSizes().subscribe(sizes => {
      this.sizes = sizes;
      this.selectedSize = this.sizes[0];
      this.updatePrice();
    });

    this.doughService.getAllDoughTypes().subscribe(doughs => {
      this.doughs = doughs;
      this.selectedDough = this.doughs[0];
      this.updatePrice();
    });

    this.toppingService.getAllToppings().subscribe(toppings => {
      this.toppings = toppings;
    });

    this.selectedCheese = this.cheeses[0];
  }

  toggleTopping(topping: any) {
    const index = this.selectedToppings.findIndex(t => t.id === topping.id);
    if (index > -1) {
      this.selectedToppings.splice(index, 1);
    } else {
      this.selectedToppings.push(topping);
    }
    this.updatePrice();
  }

  updatePrice() {
    if (!this.pizza) return;

    let price = this.pizza.basePrice;
    if (this.selectedSize) price *= this.selectedSize.priceMultiplier;
    if (this.selectedDough) price += this.selectedDough.extraPrice;
    if (this.selectedCheese) price += this.selectedCheese.extraPrice;
    if (this.selectedToppings.length) {
      this.selectedToppings.forEach(t => price += t.basePrice);
    }
    this.totalPrice = parseFloat(price.toFixed(2));
  }

  addToCart() {

    console.log('Agregando al carrito:', {
      pizzaId: this.pizza.id,
      size: this.selectedSize,
      dough: this.selectedDough,
      cheese: this.selectedCheese,
      toppings: this.selectedToppings,
      totalPrice: this.totalPrice
    });
  }

  orderNow() {
    this.addToCart();
    //todavia no hau vista 
    this.router.navigate(['/checkout']); 
  }
}
