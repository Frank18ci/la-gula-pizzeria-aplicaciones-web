import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CarritoService } from '../../../../shared/services/carrito/carrito-service';
import { DoughTypeService } from '../../../../shared/services/catalog/dough-type-service';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { SizeService } from '../../../../shared/services/catalog/size-service';
import { ToppingService } from '../../../../shared/services/catalog/topping-service';

import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import AuthResponse from '../../../../shared/model/auth/AuthResponse.model';
import Carrito from '../../../../shared/model/carrito/carrito';
import CarritoDto from '../../../../shared/model/carrito/carritoDto';
import OrderItemRequest from '../../../../shared/model/ordening/request/orderItemRequest.model';
import OrderItemToppingRequest from '../../../../shared/model/ordening/request/orderItemToppingRequest.model';
import orderRequest, { DeliveryMethod, OrderStatus, PaymentStatus } from '../../../../shared/model/ordening/request/orderRequest.model';
import { CustomerService } from '../../../../shared/services/customer/customer-service';
import { OrderItemService } from '../../../../shared/services/ordening/order-item-service';
import { OrderItemToppingService } from '../../../../shared/services/ordening/order-item-topping-service';
import { OrderService } from '../../../../shared/services/ordening/order-service';
import { RootImagePizza } from '../../../../shared/storage/RootImagen';
import { forkJoin, switchMap } from 'rxjs';

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
    private cdr: ChangeDetectorRef,
    private cookieService: CookieService,
    private toastr: ToastrService,
    private router: Router,
    private customerService: CustomerService,
    private orderService: OrderService,
    private orderItemService: OrderItemService,
    private orderItemToppingService: OrderItemToppingService,
  ) { }

  ngOnInit(): void {
    this.loadCartItems();
    this.loadUserCookie();
  }
  usercookie!: AuthResponse;
  loadUserCookie() {
    this.usercookie = JSON.parse(this.cookieService.get('user') || '{}');
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
      if (item.quantity < 10) {
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
  generarOrden(): void {
    if (!this.usercookie.id) {
      this.toastr.error('Debes iniciar sesión para realizar la orden', 'Error de Autenticación');
      this.router.navigate(['/application']);
      return;
    }

    this.customerService.getCustomerByUserId(this.usercookie.id!).subscribe({
      next: (customer) => {
        const orderRequest: orderRequest = {
          orderNumber: 'ORD-' + new Date().getTime(),
          customerId: customer.id,
          notes: 'Orden generada desde el carrito',
          status: OrderStatus.pending,
          deliveryMethod: DeliveryMethod.DELIVERY,
          deliveryFee: 0,
          discountTotal: 0,
          paymentStatus: PaymentStatus.unpaid,
          placedAt: new Date().toISOString(),
          subtotal: this.total,
          tax: this.total * 0.18,
          total: this.total
        };

        this.orderService.saveOrder(orderRequest).subscribe({
          next: (orderResponse) => {
            const allRequests = [];

            for (const item of this.cartItems) {
              const orderItemReq: OrderItemRequest = {
                orderId: orderResponse.id,
                pizzaId: item.pizza.id,
                sizeId: item.size.id,
                doughTypeId: item.doughType.id,
                quantity: item.quantity,
                unitPrice: item.pizza.basePrice,
                lineTotal: item.subtotal,
                note: 'Orden generada desde el carrito con orderId ' + orderResponse.id
              };

              const itemObs = this.orderItemService.saveOrderItem(orderItemReq).pipe(
                switchMap((orderItemResponse) => {
                  const toppingRequests = item.toppings.map(topping => {
                    const toppingReq: OrderItemToppingRequest = {
                      orderItemId: orderItemResponse.id,
                      toppingId: topping.topping.id,
                      quantity: topping.quantity,
                      priceDelta: topping.topping.basePrice * topping.quantity,
                      action: 'ADDED'
                    };
                    return this.orderItemToppingService.saveOrderItemTopping(toppingReq);
                  });
                  return forkJoin(toppingRequests);
                })
              );

              allRequests.push(itemObs);
            }


            forkJoin(allRequests).subscribe({
              next: () => {
                this.toastr.success('Orden generada correctamente', 'Éxito');
                this.carritoService.clearCart();
                this.cartItems = [];
                this.cartItemRecive = [];
                this.total = 0;
                this.cdr.detectChanges();


                this.orderService.notifyOrder(orderResponse.id).subscribe({
                  next: () => this.toastr.info('Correo enviado al cliente'),
                  error: (e) => console.error('Error enviando notificación', e)
                });
              },
              error: (err) => {
                console.error('Error guardando detalles de la orden:', err);
                this.toastr.error('Error guardando los detalles de la orden', 'Error');
              }
            });
          },
          error: (error) => {
            console.error('Error creando la orden:', error);
            this.toastr.error('No se pudo generar la orden', 'Error');
          }
        });
      },
      error: (error) => {
        console.error('Error obteniendo cliente:', error);
        this.toastr.error('No se pudo obtener la información del cliente', 'Error');
      }
    });
  }
}
