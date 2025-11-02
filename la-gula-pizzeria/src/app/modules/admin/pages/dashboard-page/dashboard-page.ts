import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import CustomerResponse from '../../../../shared/model/customer/response/customerResponse.model';
import orderResponse from '../../../../shared/model/ordening/response/orderResponse.model';
import OrderPaymentResponse from '../../../../shared/model/payment/response/orderPaymentResponse.model';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { CustomerService } from '../../../../shared/services/customer/customer-service';
import { OrderService } from '../../../../shared/services/ordening/order-service';
import { OrderPaymentService } from '../../../../shared/services/payment/order-payment-service';

@Component({
  selector: 'app-dashboard-page',
  imports: [CommonModule],
  templateUrl: './dashboard-page.html',
  styleUrl: './dashboard-page.css'
})
export class DashboardPage implements OnInit {
  orders: orderResponse[] = [];
  customers: CustomerResponse[] = [];
  ordersPayments: OrderPaymentResponse[] = [];
  orderCount: number = 0;
  customerCount: number = 0;
  totalRevenue: number = 0;
  pizzas: PizzaResponse[] = [];
  constructor(
    private orderService: OrderService,
    private customerService: CustomerService,
    private orderPaymentService: OrderPaymentService,
    private cdRef: ChangeDetectorRef,
    private pizzaService: PizzaService
  ) {

  }
  ngOnInit(): void {
    this.loadOrders();
    this.loadCustomers();
    this.loadOrdersPayments();
    this.loadPizzas();
  }
  loadPizzas(): void {
    this.pizzaService.getAllPizzas().subscribe({
      next: (pizzas: PizzaResponse[]) => {
        this.pizzas = pizzas;
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading pizzas:', error);
      }
    });
  }
  loadOrders(): void {
    this.orderService.getAllOrders().subscribe({
      next: (orders: orderResponse[]) => {
        this.orders = orders;
        orders.forEach(order => this.addOrderByStatus(order.status));
        console.log(this.orderByStatus);
        this.orderCount = orders.length;
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading orders:', error);
      }
    });
  }
  loadCustomers(): void {
    this.customerService.getAllCustomers().subscribe({
      next: (customers: CustomerResponse[]) => {
        this.customers = customers;
        this.customerCount = customers.length;
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading customers:', error);
      }
    });
  }
  loadOrdersPayments(): void {
    this.orderPaymentService.getAllOrders().subscribe({
      next: (ordersPayments: OrderPaymentResponse[]) => {
        this.ordersPayments = ordersPayments;
        this.totalRevenue = ordersPayments.reduce((sum, order) => sum + order.totalAmount, 0);
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading order payments:', error);
      }
    });
  }
  orderByStatus: { [key: string]: number } = {
  }
  addOrderByStatus(status: string): void {
    if (!this.orderByStatus[status]) {
      this.orderByStatus[status] = 0;
    }
    this.orderByStatus[status]++;
  }
  getKeyValues(): { key: string, value: number }[] {
    return Object.entries(this.orderByStatus).map(([key, value]) => ({ key, value }));
  }
  getBarWidth(value: number): number {
    const max = Math.max(...Object.values(this.orderByStatus));
    return Math.min((value / max) * 100, 100);
  }
}
