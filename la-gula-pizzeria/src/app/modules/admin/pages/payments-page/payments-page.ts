import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { PaymentService } from '../../../../shared/services/payment/payment-service';
import { MatDialog } from '@angular/material/dialog';
import { PaymentDialog } from '../../components/dialogs/payment-dialog/payment-dialog';
import PaymentResponse from '../../../../shared/model/payment/response/paymentResponse.model';

@Component({
  selector: 'app-payments-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './payments-page.html',
  styleUrl: './payments-page.css'
})
export class PaymentsPage {
  displayedColumns: string[] = ['id', 'paymentStatus', 'currency', 'paymentStatusName', 'amount','createdAt', 'Acciones'];
  dataSource = new MatTableDataSource<PaymentResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
      private paymentService : PaymentService,
      private dialog: MatDialog
    ) { }
    ngOnInit(): void {
      this.loadpayment();
    }
    ngAfterViewInit(): void {
          this.dataSource.paginator = this.paginator;
    }
    loadpayment(): void {
          this.paymentService.getAllPayments().subscribe({
            next: (payment: PaymentResponse[]) => {
              this.dataSource.data = payment;
              
            },
            error: (error) => {
              console.error('Error loading Pagos:', error);
            }
          });
    }  
    openDialog(payment?: PaymentResponse): void {
          let paymentDialogData: any = {};
              if (payment) {
                paymentDialogData = { ...payment };
              }
              const dialogRef = this.dialog.open(PaymentDialog, {
                width: '700px',
                data: payment ? paymentDialogData : null
              });
              dialogRef.afterClosed().subscribe(result => {
                if (result) {
                  if(paymentDialogData.id) {
                    this.paymentService.updatePayment(paymentDialogData.id, result).subscribe({
                      next: () => {
                        this.loadpayment();
                      },
                      error: (error) => {
                        console.error('Error updating payment:', error);
                      }
                    });
                  } else {
                    this.paymentService.savePayment(result).subscribe({
                      next: () => {
                        this.loadpayment();
                      },
                      error: (error) => {
                        console.error('Error creating payment:', error);
                      }
                    });
                  }
                }
              });
            }
    viewpaymentDetails(payment: PaymentResponse): void {
      // Implementation for viewing user details goes here
    }
    deletepayment(paymentId: number): void {
      if(confirm('¿Estás seguro de que deseas eliminar esta Pago?')) {
        this.paymentService.deletePayment(paymentId).subscribe({
          next: () => {
            this.loadpayment();
          },
          error: (error) => {
            console.error('Error deleting orden:', error);
          }
        });
      }
    }
    
    applyFilter(event: Event): void {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
}
