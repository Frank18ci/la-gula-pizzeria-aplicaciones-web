import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import DeliveryRequest, { DeliveryMethod, DeliveryStatus } from '../../../../../shared/model/delivery/request/deliveryRequest.model';
import DeliveryResponse from '../../../../../shared/model/delivery/response/deliveryResponse.model';
import orderResponse from '../../../../../shared/model/ordening/response/orderResponse.model';
import AdressResponse from '../../../../../shared/model/customer/response/adressResponse.model';
import { OrderService } from '../../../../../shared/services/ordening/order-service';
import { AdressService } from '../../../../../shared/services/customer/adress-service';

@Component({
  selector: 'app-delivery-dialog',
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
  templateUrl: './delivery-dialog.html',
  styleUrl: './delivery-dialog.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DeliveryDialog implements OnInit{
  form!: FormGroup;
  deliveryRequest: DeliveryRequest = {
    orderId: 0,
    addressId: 0,
    method: DeliveryMethod.DELIVERY,
    status: DeliveryStatus.pending,
    driverName: "",
    driverPhone: "",
    instructions: ""
  }
  deliveryId?: number;
  orders: orderResponse[] = [];
  addresses: AdressResponse[] = [];
  constructor(
    private dialogRef: MatDialogRef<DeliveryDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DeliveryResponse,
    private fb: FormBuilder,
    private cdRef: ChangeDetectorRef,
    private orderService: OrderService,
    private adressService: AdressService
  ) {
    if(data) {
      this.deliveryRequest = {
        orderId: data.orderId ?? 0,
        addressId: data.addressId ?? 0,
        method: DeliveryMethod[data.method as keyof typeof DeliveryMethod] ?? DeliveryMethod.DELIVERY,
        status: DeliveryStatus[data.status as keyof typeof DeliveryStatus] ?? DeliveryStatus.pending,
        driverName: data.driverName ?? '',
        driverPhone: data.driverPhone ?? '',
        instructions: data.instructions ?? '',
      }
      this.deliveryId = data.id ?? undefined;
    }
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      orderId: [this.deliveryRequest.orderId, [Validators.required]],
      addressId: [this.deliveryRequest.addressId, [Validators.required]],
      method: [this.deliveryRequest.method, [Validators.required]],
      status: [this.deliveryRequest.status, [Validators.required]],
      driverName: [this.deliveryRequest.driverName, []],
      driverPhone: [this.deliveryRequest.driverPhone, []],
      instructions: [this.deliveryRequest.instructions, [Validators.required]]      
    });
    
    this.orderService.getAllOrders().subscribe({
      next: (orders: orderResponse[]) => {
        this.orders = orders;
        if(this.deliveryRequest.orderId != 0) {
          this.form.patchValue({ orderId: this.deliveryRequest.orderId });
        }
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading orders:', error);
      }
    });
    this.adressService.getAllAdresses().subscribe({
      next: (addresses: AdressResponse[]) => {
        this.addresses = addresses;
        if(this.deliveryRequest.addressId != 0) {
          this.form.patchValue({ addressId: this.deliveryRequest.addressId });
        }
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading address:', error);
      }
    });
  }
  saveDelivery(): void {
    if(this.form.invalid) {
      return;
    }
    
    const deliveryData: DeliveryRequest = {
      ...this.deliveryRequest,
      ...this.form.value
    };
    this.dialogRef.close(deliveryData);
  }
  closeDialog() {
    this.dialogRef.close();
  }
}
