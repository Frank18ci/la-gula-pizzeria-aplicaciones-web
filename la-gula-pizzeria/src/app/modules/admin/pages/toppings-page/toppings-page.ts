import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { RootImageTopping } from '../../../../shared/storage/RootImagen';
import ToppingResponse from '../../../../shared/model/catalog/response/toppingResponse.model';
import { ToppingService } from '../../../../shared/services/catalog/topping-service';
import { MatDialog } from '@angular/material/dialog';
import { ToppingDialog } from '../../components/dialogs/topping-dialog/topping-dialog';

@Component({
  selector: 'app-toppings-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './toppings-page.html',
  styleUrl: './toppings-page.css'
})
export class ToppingsPage implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['id', 'image', 'name', 'basePrice', 'category', 'isVegetarian', 'active', 'Acciones'];
  dataSource = new MatTableDataSource<ToppingResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  rootImage = RootImageTopping;
  constructor(
    private toppingService: ToppingService,
    private dialog: MatDialog
  ) { }
  ngOnInit(): void {
    this.loadToppings();
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }
  loadToppings(): void {
    this.toppingService.getAllToppings().subscribe({
      next: (toppings: ToppingResponse[]) => {
        this.dataSource.data = toppings;
      },
      error: (error) => {
        console.error('Error loading toppings:', error);
      }
    });
  }
  openDialog(topping?: ToppingResponse): void {
    let toppingDialogData: any = {};
    if (topping) {
      toppingDialogData = { ...topping };
    }
    const dialogRef = this.dialog.open(ToppingDialog, {
      width: '700px',
      data: topping ? toppingDialogData : null
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadToppings();
      }
    });
  }
  viewToppingDetails(topping: ToppingResponse): void {
    // Implementa the logic to view topping details
  }
  deleteTopping(toppingId: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este topping?')) {
      this.toppingService.deleteTopping(toppingId).subscribe({
        next: () => {
          this.loadToppings();
        },
        error: (error) => {
          console.error('Error deleting topping:', error);
        }
      });
    }
  }
  buscarToppingsByNombre(nombre: string): void {
    this.toppingService.getAllToppingsByName(nombre).subscribe({
      next: (toppings: ToppingResponse[]) => {
        this.dataSource.data = toppings;
      },
      error: (error) => {
        console.error('Error searching toppings by name:', error);
      }
    });
  }
  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.buscarToppingsByNombre(filterValue.trim().toLowerCase());
  }
}
