import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CarritoService } from '../../../../shared/services/carrito/carrito-service';
import { DoughTypeService } from '../../../../shared/services/catalog/dough-type-service';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { SizeService } from '../../../../shared/services/catalog/size-service';
import { ToppingService } from '../../../../shared/services/catalog/topping-service';

import Carrito from '../../../../shared/model/carrito/carrito';
import CarritoDto from '../../../../shared/model/carrito/carritoDto';
import { RootImagePizza } from '../../../../shared/storage/RootImagen';

@Component({
  selector: 'app-carrito-page',
  imports: [CommonModule],
  templateUrl: './carrito-page.html',
  styleUrls: ['./carrito-page.css']
})
export class CarritoPage implements OnInit {
  rootImagePizza = RootImagePizza;
  cartItems: CarritoDto[] = [];
  cartItemRecive: Carrito[] = [];

  constructor(
    private carritoService: CarritoService,
    private pizzaService: PizzaService,
    private sizeService: SizeService,
    private doughService: DoughTypeService,
    private toppingService: ToppingService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadCartItems();
  }
  total: number = 0;
  private async loadCartItems(): Promise<void> {
    const carritoRecibido: Carrito[] = this.carritoService.getCart();
    this.cartItemRecive = carritoRecibido;
    for (const item of carritoRecibido) {
      const pizza = await this.pizzaService.getPizzaById(item.pizzaId).toPromise();
      const size = await this.sizeService.getSizeById(item.sizeId).toPromise();
      const dough = await this.doughService.getDoughTypeById(item.doughTypeId).toPromise();

      const toppingDetails = await Promise.all(
        item.toppings.map(async toppingItem => {
          const toppingDetail = await this.toppingService.getToppingById(toppingItem.toppingId).toPromise();
          return {
            topping: toppingDetail!,
            quantity: toppingItem.quantity
          };
        })
      );

      if (!pizza || !size || !dough) {
        console.error('Error loading cart item details');
        continue;
      }

      this.cartItems.push({
        id: item.id,
        pizza,
        quantity: item.quantity,
        doughType: dough,
        size,
        toppings: toppingDetails,
        subtotal: pizza.basePrice * item.quantity + toppingDetails.reduce((sum, t) => sum + (t.topping.basePrice * t.quantity), 0)
      });
      this.total += pizza.basePrice * item.quantity + toppingDetails.reduce((sum, t) => sum + (t.topping.basePrice * t.quantity), 0);
      this.cdr.detectChanges();
    }
  }
  aumentarCantidad(id: number): void {
    const item = this.cartItemRecive.find(item => item.id === id);
    if (item) {
      if(item.quantity < 10){
        item.quantity += 1;
      }
      this.carritoService.saveCart(this.cartItemRecive);
      this.total = 0;
      this.cartItems = [];
      this.loadCartItems();
      this.cdr.detectChanges();
    }
  }
  disminuirCantidad(id: number): void {
    const item = this.cartItemRecive.find(item => item.id === id);
    if (item) {
      item.quantity = Math.max(1, item.quantity - 1);
      this.carritoService.saveCart(this.cartItemRecive);
      this.total = 0;
      this.cartItems = [];
      this.loadCartItems();
      this.cdr.detectChanges();
    }
  }
  eliminarItem(id: number): void {
    this.carritoService.deleteItemById(id);
    this.total = 0;
    this.cartItems = [];
    this.loadCartItems();
    this.cdr.detectChanges();
  }
}
