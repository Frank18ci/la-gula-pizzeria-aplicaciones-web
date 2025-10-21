import { DeliveryService } from './../../../../shared/services/delivery/delivery-service';
import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import DeliveryResponse from '../../../../shared/model/delivery/response/deliveryResponse.model';

@Component({
  selector: 'app-deliveries-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './deliveries-page.html',
  styleUrl: './deliveries-page.css'
})
export class DeliveriesPage implements OnInit, AfterViewInit{
  displayedColumns: string[] = ['id', 'method', 'status', 'driverName', 'driverPhone', 'instructions', 'Acciones'];
  dataSource = new MatTableDataSource<DeliveryResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    private deliveryService: DeliveryService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
      this.loadDelivery();
    }
    ngAfterViewInit(): void {
      this.dataSource.paginator = this.paginator;
    }
    loadDelivery(): void {
      this.deliveryService.getAllDeliveries().subscribe({
        next: (users: DeliveryResponse[]) => {
          this.dataSource.data = users;
        },
        error: (error) => {
          console.error('Error loading users:', error);
        }
      });
    }  
    openDialog(delivery?: DeliveryResponse): void {
      // Implementation for opening user dialog goes here
    }
    viewDeliveryDetails(delivery: DeliveryResponse): void {
      // Implementation for viewing user details goes here
    }
    deleteDelivery(deliveryId: number): void {
      if(confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
        this.deliveryService.deleteDelivery(deliveryId).subscribe({
          next: () => {
            this.loadDelivery();
          },
          error: (error) => {
            console.error('Error deleting Delivery:', error);
          }
        });
      }
    }
    buscarDeliveryPorNombre(driverName: string): void {
      this.deliveryService.getDeliveriesByDriverName(driverName).subscribe({
        next: (users: DeliveryResponse[]) => {
          this.dataSource.data = users;
        },
        error: (error) => {
          console.error('Error searching Delivery by driverName:', error);
        }
      });
    }
    applyFilter(event: Event): void {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
  
}

