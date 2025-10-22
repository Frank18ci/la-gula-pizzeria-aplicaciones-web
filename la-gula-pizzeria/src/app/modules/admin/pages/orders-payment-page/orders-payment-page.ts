import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import OrderPaymentResponse from '../../../../shared/model/payment/response/orderPaymentResponse.model';
import { OrderPaymentDialog } from '../../components/dialogs/order-payment-dialog/order-payment-dialog';
import { OrderPaymentService } from '../../../../shared/services/payment/order-payment-service';

@Component({
  selector: 'app-orders-payment-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './orders-payment-page.html',
  styleUrl: './orders-payment-page.css'
})
export class OrdersPaymentPage {
  displayedColumns: string[] = ['id', 'customerName', 'totalAmount', 'Acciones'];
  dataSource = new MatTableDataSource<OrderPaymentResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    private orderPaymentService: OrderPaymentService,
    private MatDialog: MatDialog
  ) { }
  ngOnInit(): void {
    this.loadorder();
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }
  loadorder(): void {
    this.orderPaymentService.getAllOrders().subscribe({
      next: (orders: OrderPaymentResponse[]) => {

        this.dataSource.data = orders;
      },
      error: (error) => {
        console.error('Error loading orders:', error);
      }
    });
  }
  openDialog(order?: OrderPaymentResponse): void {
    let orderDialogData: any = {};
    if (order) {
      orderDialogData = { ...order };
    }
    const dialogRef = this.MatDialog.open(OrderPaymentDialog, {
      width: '700px',
      data: order ? orderDialogData : null
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (orderDialogData.id) {
          this.orderPaymentService.updateOrder(orderDialogData.id, result).subscribe({
            next: () => {
              this.loadorder();
            },
            error: (error) => {
              console.error('Error updating order:', error);
            }
          });
        } else {
          this.orderPaymentService.saveOrder(result).subscribe({
            next: () => {
              this.loadorder();
            },
            error: (error) => {
              console.error('Error saving order:', error);
            }
          });
        }
      }
    });
  }
  viewOrderDetails(order: OrderPaymentResponse): void {
    // Implementation for viewing user details goes here
  }
  deleteOrder(orderId: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta Orden?')) {
      this.orderPaymentService.deleteOrder(orderId).subscribe({
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

