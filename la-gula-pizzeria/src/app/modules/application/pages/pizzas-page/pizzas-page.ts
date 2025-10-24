import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
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
import SizeResponse from '../../../../shared/model/catalog/response/SizeResponse.model';
import { SizeService } from '../../../../shared/services/catalog/size-service';
import DoughTypeResponse from '../../../../shared/model/catalog/response/doughTypeResponse.model';
import { DoughTypeService } from '../../../../shared/services/catalog/dough-type-service';

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
  sizes: SizeResponse[] = [];
  sizeSelected: boolean = false;
  doughs: DoughTypeResponse[] = [];
  doughSelected: boolean = false;

  constructor(
    private pizzaService: PizzaService,
    private router: Router,
    private sizeService: SizeService,
    private doughTypeService: DoughTypeService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    // priceRange is already initialized on the field; ensure pizzas load asynchronously
    this.pizzaService.getAllPizzas().subscribe({
      next: data => {
        this.pizzas = data;
        this.filteredPizzas = [...this.pizzas];
        this.cdr.detectChanges();
      },
      error: err => console.error(err)
    });
    this.sizeService.getAllSizes().subscribe({
      next: data => {
        this.sizes = data;
        this.cdr.detectChanges();
      },
      error: err => console.error(err)
    });
    this.doughTypeService.getAllDoughTypes().subscribe({
      next: data => {
        this.doughs = data;
        this.cdr.detectChanges();
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
    this.filteredPizzas = [...this.pizzas];
  }
}
