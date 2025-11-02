import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import CustomerResponse from '../../../../shared/model/customer/response/customerResponse.model';
import { CustomerService } from '../../../../shared/services/customer/customer-service';
import { MatDialog } from '@angular/material/dialog';
import { ClientDialog } from '../../components/dialogs/client-dialog/client-dialog';
import { UserService } from '../../../../shared/services/user/user-service';
import UserResponse from '../../../../shared/model/user/response/userResponse.model';

@Component({
  selector: 'app-clients-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './clients-page.html',
  styleUrl: './clients-page.css'
})
export class ClientsPage implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['id', 'userName', 'loyaltyPoints', 'birthDate', 'notes', 'Acciones'];
  dataSource = new MatTableDataSource<CustomerResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  users: UserResponse[] = [];
  constructor(
    private customerService: CustomerService,
    private dialog: MatDialog,
    private userService: UserService
  ) { }
  ngOnInit(): void {
    this.loadCustomers();
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }
  loadCustomers(): void {
    this.customerService.getAllCustomers().subscribe({
      next: (customers: CustomerResponse[]) => {
        this.dataSource.data = customers;
        this.loadUsers();
      },
      error: (error) => {
        console.error('Error loading customers:', error);
      }
    });
  }
  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.dataSource.data = this.dataSource.data.map(customer => {
          const user = users.find(u => u.id === customer.userId);
          return { ...customer, userName: user ? user.fullName : '' };
        });
      },
      error: (error) => {
        console.error('Error loading users:', error);
      }
    });
  }
  openDialog(customer?: CustomerResponse): void {
    let customerDialogData: any = {};
    if (customer) {
      customerDialogData = { ...customer };
    }
    const dialogRef = this.dialog.open(ClientDialog, {
      width: '700px',
      data: customer ? customerDialogData : null
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (customerDialogData.id) {
          this.customerService.updateCustomer(customerDialogData.id, result).subscribe({
            next: () => {
              this.loadCustomers();
            },
            error: (error) => {
              console.error('Error updating customer:', error);
            }
          });
        } else {
          this.customerService.saveCustomer(result).subscribe({
            next: () => {
              this.loadCustomers();
            },
            error: (error) => {
              console.error('Error creating customer:', error);
            }
          });
        }
      }
    });
  }
  viewCustomerDetails(customer: CustomerResponse): void {
    // Implement view details logic here
  }
  deleteCustomer(customerId: number): void {
    this.customerService.deleteCustomer(customerId).subscribe({
      next: () => {
        this.loadCustomers();
      },
      error: (error) => {
        console.error('Error deleting customer:', error);
      }
    });
  }
  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.buscarClientePorId(filterValue.trim().toLowerCase());
  }
  buscarClientePorId(id: string) {
    if (id === '') {
      this.loadCustomers();
      return;
    }
    this.customerService.getCustomerById(Number(id)).subscribe({
      next: (customer: CustomerResponse) => {
        this.dataSource.data = [customer];
      },
      error: (error) => {
        this.loadCustomers();
      }
    });
  }
}
