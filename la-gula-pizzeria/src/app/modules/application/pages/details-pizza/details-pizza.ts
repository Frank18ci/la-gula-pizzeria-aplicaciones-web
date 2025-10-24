import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { SizeService } from '../../../../shared/services/catalog/size-service';
import { DoughTypeService } from '../../../../shared/services/catalog/dough-type-service';
import { ToppingService } from '../../../../shared/services/catalog/topping-service';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import { MaterialModule } from '../../../../shared/modules/material-module.module';
import { CommonModule } from '@angular/common';
import ToppingResponse from '../../../../shared/model/catalog/response/toppingResponse.model';
import { RootImagePizza, RootImageTopping } from '../../../../shared/storage/RootImagen';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


interface CheeseOption {
  name: string;
  extraPrice: number;
}

@Component({
  selector: 'app-details-pizza',
  imports: [CommonModule,MaterialModule,FormsModule, ReactiveFormsModule],
  templateUrl: './details-pizza.html',
  styleUrls: ['./details-pizza.css']
})
export class DetailsPizzaComponent  implements OnInit {
  
    pizza: PizzaResponse = {
      id: 0,
      name: '',
      description: '',
      image: '',
      basePrice: 0,
      toppings: [],
      active: false
    };
    pizzaId!: number;
    sizes: any[] = [];
    doughs: any[] = [];
    toppings: ToppingResponse[] = [];

    rootImagePizza = RootImagePizza;
    rootImageTopping = RootImageTopping;
    

  cheeses: CheeseOption[] = [
      { name: 'Mozzarella', extraPrice: 0 },
      { name: 'Cheddar', extraPrice: 1.5 },
      { name: 'Parmesan', extraPrice: 2 }
  ];

  selectedSize: any = null;
  selectedDough: any = null;
  selectedCheese: CheeseOption | null = null;
  selectedToppings: any[] = [];

  totalPrice: number = 0;

  constructor(
    private route: ActivatedRoute,
    private pizzaService: PizzaService,
    private sizeService: SizeService,
    private doughService: DoughTypeService,
    private toppingService: ToppingService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ){}


  ngOnInit(): void {
    const pizzaId = this .route.snapshot.params['id'];
    
    this.pizzaService.getPizzaById(pizzaId).subscribe({
      next: data => {
        this.pizza = data;
        this.selectedToppings = [...(data.toppings || [])];
        this.updatePrice();
        this.cdr.detectChanges();
      }
    });

    this.sizeService.getAllSizes().subscribe(sizes => {
      this.sizes = sizes;
      this.selectedSize = this.sizes[0];
      this.updatePrice();
      this.cdr.detectChanges();
    });

    this.doughService.getAllDoughTypes().subscribe(doughs => {
      this.doughs = doughs;
      this.selectedDough = this.doughs[0];
      this.updatePrice();
      this.cdr.detectChanges();
    });

    this.toppingService.getAllToppings().subscribe(toppings => {
      this.toppings = toppings;
      this.cdr.detectChanges();
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

getToppingsTotal(): number {
 if (!this.selectedToppings?.length) return 0;
  return this.selectedToppings.reduce((a, b) => a + (b.basePrice || 0), 0);
}

isToppingSelected(topping: any): boolean {
  return this.selectedToppings?.some(t => t.id === topping.id) || false;
}


  orderNow() {
    this.addToCart();
    //todavia no hau vista 
    this.router.navigate(['/checkout']); 
  }
}
