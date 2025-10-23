import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { OrderService } from '../../../../shared/services/ordening/order-service';
import OrderResponse from '../../../../shared/model/payment/response/orderPaymentResponse.model';
import CustomerResponse from '../../../../shared/model/customer/response/customerResponse.model';
import { CustomerService } from '../../../../shared/services/customer/customer-service';
import orderResponse from '../../../../shared/model/ordening/response/orderResponse.model';
import { OrderPaymentService } from '../../../../shared/services/payment/order-payment-service';
import OrderPaymentResponse from '../../../../shared/model/payment/response/orderPaymentResponse.model';

@Component({
  selector: 'app-dashboard-page',
  imports: [],
  templateUrl: './dashboard-page.html',
  styleUrl: './dashboard-page.css'
})
export class DashboardPage implements OnInit{
  orders: orderResponse[] = [];
  customers: CustomerResponse[] = [];
  ordersPayments: OrderPaymentResponse[] = [];
  orderCount: number = 0;
  customerCount: number = 0;
  totalRevenue: number = 0;
  constructor(
    private orderService: OrderService,
    private customerService: CustomerService,
    private orderPaymentService: OrderPaymentService,
    private cdRef: ChangeDetectorRef
  ) {

  }
  ngOnInit(): void {
    this.loadOrders();
    this.loadCustomers();
    this.loadOrdersPayments();
  }
  loadOrders(): void {
    this.orderService.getAllOrders().subscribe({
      next: (orders: orderResponse[]) => {
        this.orders = orders;
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
}
