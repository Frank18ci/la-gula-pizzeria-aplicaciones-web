import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import PaymentRequest from '../../../../../shared/model/payment/request/paymentRequest.model';
import { PaymentService } from '../../../../../shared/services/payment/payment-service';
import PaymentResponse from '../../../../../shared/model/payment/response/paymentResponse.model';


@Component({
  selector: 'app-payment-dialog',
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
  templateUrl: './payment-dialog.html',
  styleUrl: './payment-dialog.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PaymentDialog implements OnInit{
form!: FormGroup;
  paymentRequest: PaymentRequest = {
    orderId: 0,
    amount: 0,
    currency: '',
    paymentProviderId: 0,
    
    paymentStatusId: 0,
    externalId: '',
    processedAt: '' 
  }
  paymentId?: number;
  payments: PaymentResponse[] = [];

  constructor(
    private dialogRef: MatDialogRef<PaymentDialog>,
    @Inject(MAT_DIALOG_DATA) public data: PaymentResponse,
    private fb: FormBuilder,
    private cdRef: ChangeDetectorRef,
    private paymentService: PaymentService
  ) {
    if(data) {
      this.paymentRequest = {
        orderId: data.order.id ?? 0,
        amount: data.amount ?? 0,
        currency: data.currency ?? '',
        paymentProviderId: data.paymentProvider.id ?? 0,
        paymentStatusId: data.paymentStatus.id ?? 0,
        externalId: data.externalId ?? '',
        processedAt: data.processedAt ? new Date(data.processedAt).toISOString().split('T')[0] : '',

      }
      this.paymentId = data.id ?? undefined;
    }
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      order: [this.paymentRequest.orderId, [Validators.required]],
      amount: [this.paymentRequest.amount, [Validators.required]],
      currency: [this.paymentRequest.currency, [Validators.required]],
      paymentProvider: [this.paymentRequest.paymentProviderId, [Validators.required]],
      paymentStatus: [this.paymentRequest.paymentStatusId, [Validators.required]],
      externalId: [this.paymentRequest.externalId, [Validators.required]],
      processedAt: [this.paymentRequest.processedAt, [Validators.required]]
    });
    this.paymentService.getAllPayments().subscribe({
      next: (payments) => {
        this.payments = payments;
        if(this.paymentRequest.orderId != 0){
          this.form.patchValue({ orderId: this.paymentRequest.orderId });
        }
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading Payments:', error);
      }
    }); 
  }
  savePayments(): void {
    console.log('Enviando a backend:', this.form.value);
      if(this.form.invalid) {
        
        return;
      }
      this.form.value.processedAt = new Date(this.form.value.processedAt).toISOString();
      
      const customerData: PaymentRequest = {
        ...this.paymentRequest,
        ...this.form.value
      };
      
      this.dialogRef.close(customerData);
    }
    closeDialog() {
      this.dialogRef.close();
    }
}
