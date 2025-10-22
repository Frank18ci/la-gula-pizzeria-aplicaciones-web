import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import OrderPaymentRequest from '../../../../../shared/model/payment/request/orderPaymentRequest.model';
import OrderPaymentResponse from '../../../../../shared/model/payment/response/orderPaymentResponse.model';
import { UserService } from '../../../../../shared/services/user/user-service';
import UserResponse from '../../../../../shared/model/user/response/userResponse.model';

@Component({
  selector: 'app-order-payment-dialog',
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatSelectModule,
    ReactiveFormsModule
  ],
  templateUrl: './order-payment-dialog.html',
  styleUrl: './order-payment-dialog.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderPaymentDialog implements OnInit {
  form!: FormGroup;

  orderPaymentRequest: OrderPaymentRequest = {
    customerName: '',
    totalAmount: 0
  };
  orderPaymentId?: number;
  users: UserResponse[] = [];

  constructor(
    private dialogRef: MatDialogRef<OrderPaymentDialog>,
    @Inject(MAT_DIALOG_DATA) public data: OrderPaymentResponse,
    private fb: FormBuilder,
    private userService: UserService,
    private cdRef: ChangeDetectorRef
  ) {
    if(data) {
      this.orderPaymentRequest = {
        customerName: data.customerName ?? '',
        totalAmount: data.totalAmount ?? 0
      }
      this.orderPaymentId = data.id ?? undefined;
    }
  }

  ngOnInit() {
    this.form = this.fb.group({
      customerName: [this.orderPaymentRequest.customerName, [Validators.required]],
      totalAmount: [this.orderPaymentRequest.totalAmount, [Validators.required, Validators.min(0)]]
    });
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        if(this.orderPaymentRequest.customerName){
          this.form.patchValue({
            customerName: this.users.find(user => user.fullName === this.orderPaymentRequest.customerName)?.fullName
          });
        }
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading users:', error);
      }
    });
  }
  saveOrderPayment() {
    if (this.form.invalid) {
      return;
    }
    const orderPaymentData: OrderPaymentRequest = {
      ...this.orderPaymentRequest,
      ...this.form.value
    };
    this.dialogRef.close(orderPaymentData);
  }
  closeDialog(): void {
    this.dialogRef.close();
  }
}
