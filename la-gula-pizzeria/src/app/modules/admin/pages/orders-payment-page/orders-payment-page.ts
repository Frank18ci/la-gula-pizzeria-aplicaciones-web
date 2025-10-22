import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import OrderResponse from '../../../../shared/model/payment/response/orderResponse.model';
import { OrderService } from '../../../../shared/services/payment/order-service';

@Component({
  selector: 'app-orders-payment-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './orders-payment-page.html',
  styleUrl: './orders-payment-page.css'
})
export class OrdersPaymentPage {
displayedColumns: string[] = ['id', 'customerName', 'totalAmount', 'Acciones'];
  dataSource = new MatTableDataSource<OrderResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    private orderService: OrderService,
    private MatDialog: MatDialog
  ) { }
  ngOnInit(): void {
      this.loadorder();
  }
  ngAfterViewInit(): void {
      this.dataSource.paginator = this.paginator;
  }
  loadorder(): void {
        this.orderService.getAllOrders().subscribe({
          next: (orders: OrderResponse[]) => {
            
            this.dataSource.data = orders;
          },
          error: (error) => {
            console.error('Error loading orders:', error);
          }
        });
  }  
    openDialog(order?: OrderResponse): void {
      // Implementation for opening user dialog goes here
    }
    viewOrderDetails(order: OrderResponse): void {
      // Implementation for viewing user details goes here
    }
    deleteOrder(orderId: number): void {
      if(confirm('¿Estás seguro de que deseas eliminar esta Orden?')) {
        this.orderService.deleteOrder(orderId).subscribe({
          next: () => {
            this.loadorder();
          },
          error: (error) => {
            console.error('Error deleting orden:', error);
          }
        });
      }
    }
    /*buscarorderPorNombre(orderNumber: string): void {
      this.orderService.getDeliveriesByOrderNumber(orderNumber).subscribe({
        next: (users: orderResponse[]) => {
          this.dataSource.data = users;
        },
        error: (error) => {
          console.error('Error searching order by Order Number:', error);
        }
      });
    }*/
    applyFilter(event: Event): void {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
    
}
    
    