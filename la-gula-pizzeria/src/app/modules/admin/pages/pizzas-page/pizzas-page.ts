import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import PizzaResponse from '../../../../shared/model/catalog/response/pizzaResponse.model';
import { PizzaService } from '../../../../shared/services/catalog/pizza-service';
import { MatDialog } from '@angular/material/dialog';
import { PizzaDialog } from '../../components/dialogs/pizza-dialog/pizza-dialog';
import { RootImagePizza } from '../../../../shared/storage/RootImagen';

@Component({
  selector: 'app-pizzas-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './pizzas-page.html',
  styleUrl: './pizzas-page.css'
})
export class PizzasPage implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['Id', 'Imagen', 'Nombre', 'Precio base', 'Complementos', 'Activo', 'Acciones'];
  dataSource = new MatTableDataSource<PizzaResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  rootImage = RootImagePizza;

  constructor(
    private pizzaService: PizzaService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadPizzas();
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }
  loadPizzas(): void {
    this.pizzaService.getAllPizzas().subscribe({
      next: (pizzas: PizzaResponse[]) => {
        this.dataSource.data = pizzas;
      },
      error: (error) => {
        console.error('Error loading pizzas:', error);
      }
    });
  }
  openDialog(pizza?: PizzaResponse): void {
    let pizzaDialogData: any = {};
    if (pizza) {
      pizzaDialogData = { ...pizza };
    }
    const dialogRef = this.dialog.open(PizzaDialog, {
      width: '700px',
      data: pizza ? pizzaDialogData : null
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (pizzaDialogData.id) {
          this.pizzaService.updatePizza(pizzaDialogData.id, result).subscribe({
            next: () => {
              this.loadPizzas();
            },
            error: (error) => {
              console.error('Error updating pizza:', error);
            }
          });
        } else {
          this.pizzaService.savePizza(result).subscribe({
            next: () => {
              this.loadPizzas();
            },
            error: (error) => {
              console.error('Error creating pizza:', error);
            }
          });
        }
      }
    });
  }
  viewPizzaDetails(pizza: PizzaResponse): void {
    // Implement pizza details viewing logic here
  }
  deletePizza(pizzaId: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta pizza?')) {
      this.pizzaService.deletePizza(pizzaId).subscribe({
        next: () => {
          this.loadPizzas();
        },
        error: (error) => {
          console.error('Error deleting pizza:', error);
        }
      });
    }
  }
  buscarPizzaPorNombre(nombre: string) {
    this.pizzaService.getPizzaByName(nombre).subscribe(pizza => {
      this.dataSource.data = pizza;
    });
  }
  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.buscarPizzaPorNombre(filterValue.trim().toLowerCase());
  }

  getToppingsText(pizza: any): string {
    if (!pizza.toppings || pizza.toppings.length === 0) {
      return 'Sin complementos';
    }
    return pizza.toppings.map((t: any) => t.name).join(', ');
  }
}
