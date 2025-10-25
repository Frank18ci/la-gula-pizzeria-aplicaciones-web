import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { CommonModule } from '@angular/common';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import { RootImagePizza } from '../../../../shared/storage/RootImagen';
import { MaterialModule } from '../../../../shared/modules/material-module.module';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSliderModule } from '@angular/material/slider';
import SizeResponse from '../../../../shared/model/catalog/response/SizeResponse.model';
import { SizeService } from '../../../../shared/services/catalog/size-service';
import DoughTypeResponse from '../../../../shared/model/catalog/response/doughTypeResponse.model';
import { DoughTypeService } from '../../../../shared/services/catalog/dough-type-service';
import ToppingResponse from '../../../../shared/model/catalog/response/toppingResponse.model';
import { ToppingService } from '../../../../shared/services/catalog/topping-service';

@Component({
  selector: 'app-pizzas-page',
  standalone: true,
  imports: [CommonModule, MaterialModule, FormsModule, RouterModule,
    MatSliderModule,
    MatCheckboxModule,
    MatButtonModule,
    MatIconModule, ReactiveFormsModule],
  templateUrl: './pizzas-page.html',
  styleUrls: ['./pizzas-page.css']
})
export class PizzasPageComponent implements OnInit{
  form!: FormGroup;
  pizzas: PizzaResponse[] = [];
  rootImagePizza = RootImagePizza;
  
  priceRange: number = 150;
  sizes: SizeResponse[] = [];
  sizeSelected: boolean = false;
  toppings: ToppingResponse[] = [];
  doughSelected: boolean = false;

  constructor(
    private pizzaService: PizzaService,
    private router: Router,
    private sizeService: SizeService,
    private toppingService: ToppingService,
    private cdr: ChangeDetectorRef,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      minPrice: [0],
      maxPrice: [100],
      sizeId: [0],
      toppingId: [0]
    });
  }

  ngOnInit() {
    this.loadPizzas();
    this.sizeService.getAllSizes().subscribe({
      next: data => {
        this.sizes = data;
        this.cdr.detectChanges();
      },
      error: err => console.error(err)
    });
    this.toppingService.getAllToppings().subscribe({
      next: data => {
        this.toppings = data;
        this.cdr.detectChanges();
      },
      error: err => console.error(err)
    });
  }
  loadPizzas() {
     this.pizzaService.getAllPizzas().subscribe({
      next: data => {
        this.pizzas = data;
        this.cdr.detectChanges();
      },
      error: err => console.error(err)
    });
  }

  filterPizzas() {
    const { minPrice, maxPrice, sizeId, toppingId } = this.form.value;
    this.pizzaService.getPizzaByPriceRangeAndSizeIdAndDough(minPrice, maxPrice, sizeId, toppingId).subscribe({
      next: data => {
        this.pizzas = data;
        this.cdr.detectChanges();
      },
      error: err => console.error(err)
    });
  }

  clearFilters() {
    this.form.patchValue({
      minPrice: 0,
      maxPrice: 100,
      sizeId: [0],
      toppingId: [0]
    });
    this.loadPizzas();
  }
}
