
import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import orderResponse from '../../../../shared/model/ordening/response/orderResponse.model';
import { MatDialog } from '@angular/material/dialog';
import { OrderService } from '../../../../shared/services/ordening/order-service';
import { OrderOrdeningDialog } from '../../components/dialogs/order-ordening-dialog/order-ordening-dialog';

@Component({
  selector: 'app-orders-ordening-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './orders-ordening-page.html',
  styleUrl: './orders-ordening-page.css'
})
export class OrdersOrdeningPage implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['id', 'orderNumber', 'status', 'orderMethod', 'discountTotal','paymentStatus', 'Acciones'];
  dataSource = new MatTableDataSource<orderResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    private orderService: OrderService,
    private dialog: MatDialog
  ) { }
  ngOnInit(): void {
      this.loadorder();
  }
  ngAfterViewInit(): void {
      this.dataSource.paginator = this.paginator;
  }
  loadorder(): void {
        this.orderService.getAllOrders().subscribe({
          next: (orders: orderResponse[]) => {
            
            this.dataSource.data = orders;
          },
          error: (error) => {
            console.error('Error loading orders:', error);
          }
        });
  }  
    openDialog(order?: orderResponse): void {
          let orderDialogData: any = {};
              if (order) {
                orderDialogData = { ...order };
              }
              const dialogRef = this.dialog.open(OrderOrdeningDialog, {
                width: '700px',
                data: order ? orderDialogData : null
              });
              dialogRef.afterClosed().subscribe(result => {
                if (result) {
                  if(orderDialogData.id) {
                    this.orderService.updateOrder(orderDialogData.id, result).subscribe({
                      next: () => {
                        this.loadorder();
                      },
                      error: (error) => {
                        console.error('Error updating order:', error);
                      }
                    });
                  } else {
                    this.orderService.saveOrder(result).subscribe({
                      next: () => {
                        this.loadorder();
                      },
                      error: (error) => {
                        console.error('Error creating order:', error);
                      }
                    });
                  }
                }
              });
        }
    viewOrderDetails(order: orderResponse): void {
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
    buscarorderPorNombre(orderNumber: string): void {
      this.orderService.getDeliveriesByOrderNumber(orderNumber).subscribe({
        next: (users: orderResponse[]) => {
          this.dataSource.data = users;
        },
        error: (error) => {
          console.error('Error searching order by Order Number:', error);
        }
      });
    }
    applyFilter(event: Event): void {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
    
}
  
  