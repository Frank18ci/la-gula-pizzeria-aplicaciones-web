import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import orderRequest, { OrderStatus, PaymentStatus } from '../../../../../shared/model/ordening/request/orderRequest.model';
import { DeliveryMethod } from '../../../../../shared/model/delivery/request/deliveryRequest.model';
import CustomerResponse from '../../../../../shared/model/customer/response/customerResponse.model';
import { OrderService } from '../../../../../shared/services/ordening/order-service';
import orderResponse from '../../../../../shared/model/ordening/response/orderResponse.model';
import { UserService } from '../../../../../shared/services/user/user-service';
import UserResponse from '../../../../../shared/model/user/response/userResponse.model';
import { AdressService } from '../../../../../shared/services/customer/adress-service';
import AdressResponse from '../../../../../shared/model/customer/response/adressResponse.model';

@Component({
  selector: 'app-order-ordening-dialog',
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
  templateUrl: './order-ordening-dialog.html',
  styleUrl: './order-ordening-dialog.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderOrdeningDialog implements OnInit{
    form!: FormGroup;
    orderRequest: orderRequest = {
    orderNumber: '',
    customerId: 0,
    addressId: 0,
    status: OrderStatus.pending,
    deliveryMethod: DeliveryMethod.DELIVERY,
    notes: '',
    subtotal: 0,
    tax: 0,
    deliveryFee: 0,
    discountTotal: 0,
    total: 0,
    paymentStatus: PaymentStatus.unpaid,
    placedAt: ''
  }
  orderId?: number;
  users: UserResponse[] = [];
  addresses: AdressResponse[] = [];
  constructor(
    private dialogRef: MatDialogRef<OrderOrdeningDialog>,
    @Inject(MAT_DIALOG_DATA) public data: orderResponse,
    private fb: FormBuilder,
    private cdRef: ChangeDetectorRef,
    private userService: UserService,
    private adressService: AdressService
  ) {
    if(data) {
      this.orderRequest = {
        orderNumber: data.orderNumber ?? '',
        customerId: data.customerId ?? 0,
        addressId: data.addressId ?? 0,
        status: OrderStatus[data.status as keyof typeof OrderStatus] ?? OrderStatus.pending,
        deliveryMethod: DeliveryMethod[data.deliveryMethod as keyof typeof DeliveryMethod] ?? DeliveryMethod.DELIVERY,
        notes: data.notes ?? '',
        subtotal: data.subtotal ?? 0,
        tax: data.tax ?? 0,
        deliveryFee: data.deliveryFee ?? 0,
        discountTotal: data.discountTotal ?? 0,
        total: data.total ?? 0,
        paymentStatus: PaymentStatus[data.paymentStatus as keyof typeof PaymentStatus] ?? PaymentStatus.paid,
        placedAt: new Date(data.placedAt).toISOString().split('T')[0]
      }
      this.orderId = data.id ?? undefined;
    }
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      orderNumber: [this.orderRequest.orderNumber, [Validators.required]],
      customerId: [this.orderRequest.customerId, [Validators.required]],
      addressId: [this.orderRequest.addressId, [Validators.required]],
      status: [this.orderRequest.status, [Validators.required]],
      deliveryMethod: [this.orderRequest.deliveryMethod, [Validators.required]],
      notes: [this.orderRequest.notes],
      subtotal: [this.orderRequest.subtotal, [Validators.required, Validators.min(0)]],
      tax: [this.orderRequest.tax, [Validators.required, Validators.min(0)]],
      deliveryFee: [this.orderRequest.deliveryFee, [Validators.required, Validators.min(0)]],
      discountTotal: [this.orderRequest.discountTotal, [Validators.required, Validators.min(0)]],
      total: [this.orderRequest.total, [Validators.required, Validators.min(0)]],
      paymentStatus: [this.orderRequest.paymentStatus, [Validators.required]],
      placedAt: [this.orderRequest.placedAt, [Validators.required]]     
    });
    this.userService.getAllUsers().subscribe({
      next: (customers: UserResponse[]) => {
        this.users = customers;
        if(this.orderRequest.customerId != 0) {
          this.form.patchValue({ customerId: this.orderRequest.customerId });
        }
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading customers:', error);
      }
    });
    this.adressService.getAllAdresses().subscribe({
      next: (addresses: AdressResponse[]) => {
        this.addresses = addresses;
        if(this.orderRequest.addressId != 0) {
          this.form.patchValue({ addressId: this.orderRequest.addressId });
        }
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading address:', error);
      }
    });
  }
  
  saveOrder(): void {
    if (this.form.invalid) return;

    this.form.value.placedAt = new Date(this.form.value.placedAt).toISOString();
    const orderData: orderRequest = {
      ...this.orderRequest,
      ...this.form.value
    };

    this.dialogRef.close(orderData);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
