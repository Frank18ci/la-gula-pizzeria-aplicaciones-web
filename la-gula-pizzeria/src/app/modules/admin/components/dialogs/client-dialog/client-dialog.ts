import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import CustomerRequest from '../../../../../shared/model/customer/request/customerRequest.model';
import CustomerResponse from '../../../../../shared/model/customer/response/customerResponse.model';
import { UserService } from '../../../../../shared/services/user/user-service';
import UserResponse from '../../../../../shared/model/user/response/userResponse.model';

@Component({
  selector: 'app-client-dialog',
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
  templateUrl: './client-dialog.html',
  styleUrl: './client-dialog.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ClientDialog implements OnInit{
  form!: FormGroup;
  customerRequest: CustomerRequest = {
     userId: 0,
     loyaltyPoints: 0,
     birthDate: '',
     notes: ''
  }
  customerId?: number;
  users: UserResponse[] = [];
  constructor(
    private dialogRef: MatDialogRef<ClientDialog>,
    @Inject(MAT_DIALOG_DATA) public data: CustomerResponse,
    private fb: FormBuilder,
    private cdRef: ChangeDetectorRef,
    private userService: UserService
  ) {
    if(data) {
      this.customerRequest = {
        userId: data.userId ?? 0,
        loyaltyPoints: data.loyaltyPoints ?? 0,
        birthDate: new Date(data.birthDate).toISOString().split('T')[0],
        notes: data.notes ?? ''
      }
      this.customerId = data.id ?? undefined;
    }
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      userId: [this.customerRequest.userId, [Validators.required]],
      loyaltyPoints: [this.customerRequest.loyaltyPoints, [Validators.required]],
      birthDate: [this.customerRequest.birthDate, [Validators.required]],
      notes: [this.customerRequest.notes, [Validators.required]]
    });
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        if(this.customerRequest.userId != 0){
          this.form.patchValue({ userId: this.customerRequest.userId });
        }
        this.cdRef.markForCheck();
      },
      error: (error) => {
        console.error('Error loading users:', error);
      }
    }); 
  }
  saveCustomer(): void {
    if(this.form.invalid) {
      return;
    }
    this.form.value.birthDate = new Date(this.form.value.birthDate).toISOString();
    const customerData: CustomerRequest = {
      ...this.customerRequest,
      ...this.form.value
    };
    this.dialogRef.close(customerData);
  }
  closeDialog() {
    this.dialogRef.close();
  }

}
