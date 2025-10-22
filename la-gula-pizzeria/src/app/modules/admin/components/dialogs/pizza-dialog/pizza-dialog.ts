import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import PizzaRequest from '../../../../../shared/model/catalog/request/pizzaRequest.model';
import ToppingResponse from '../../../../../shared/model/catalog/response/toppingResponse.model';
import PizzaResponse from '../../../../../shared/model/catalog/response/pizzaResponse.model';
import { ToppingService } from '../../../../../shared/services/catalog/topping-service';
import { PizzaService } from '../../../../../shared/services/catalog/pizza-service';

@Component({
  selector: 'app-pizza-dialog',
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
  templateUrl: './pizza-dialog.html',
  styleUrl: './pizza-dialog.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PizzaDialog implements OnInit {
  form!: FormGroup;
  pizzaRequest: PizzaRequest = {
    name: '',
    description: '',
    image: '',
    basePrice: 0,
    active: true,
    toppingIds: []
  }
  pizzaId?: number;
  nombreImagen: string = '';
  toppings: ToppingResponse[] = [];
  constructor(
    private dialogRef: MatDialogRef<PizzaDialog>,
    @Inject(MAT_DIALOG_DATA) public data: PizzaResponse,
    private fb: FormBuilder,
    private toppingService: ToppingService,
    private pizzaService: PizzaService,
    private cdRef: ChangeDetectorRef
  ) {
    if (data) {
      this.pizzaRequest = {
        name: data.name ?? '',
        description: data.description ?? '',
        image: data.image ?? '',
        basePrice: data.basePrice ?? 0,
        active: data.active ?? true,
        toppingIds: data.toppings?.map(topping => topping.id) ?? []
      }
      this.pizzaId = data.id ?? undefined;
      this.nombreImagen = data.image ?? '';
    }
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      name: [this.pizzaRequest.name, [Validators.required]],
      description: [this.pizzaRequest.description, [Validators.maxLength(500), Validators.minLength(10), Validators.required]],
      basePrice: [this.pizzaRequest.basePrice, [Validators.required, Validators.min(0)]],
      active: [this.pizzaRequest.active, [Validators.required]],
      toppingIds: [this.pizzaRequest.toppingIds],
    });
    this.toppingService.getAllToppings().subscribe({
      next: (toppings: ToppingResponse[]) => {
        this.toppings = toppings;
        if (this.pizzaRequest.toppingIds) {
          this.form.patchValue({ toppingIds: this.pizzaRequest.toppingIds });
        }
        this.cdRef.detectChanges();
      },
      error: (error) => {
        console.error('Error loading toppings:', error);
      }
    });
  }

  selectedImageFile: File | null = null;
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const firstFile: File | null = input.files && input.files.length ? input.files[0] : null;
    this.selectedImageFile = firstFile;
    this.nombreImagen = firstFile ? firstFile.name : '';
  }

  savePizza(): void {
    if (this.form.invalid) {
      return;
    }
    const formValue = this.form.value;
    const fd = new FormData();

    Object.entries(formValue).forEach(([key, value]) => {
      if (key === 'image' || value === null || value === undefined) return;
      fd.append(key, value.toString());
    });

    if (this.selectedImageFile) {
      fd.append('image', this.selectedImageFile);
    }
    
    const request$ = this.pizzaId
      ? this.pizzaService.updatePizza(this.pizzaId, fd)
      : this.pizzaService.savePizza(fd);

    request$.subscribe({
      next: () => {
        this.dialogRef.close(true);
      },
      error: (error) => {
        console.error('Error saving pizza:', error);
      }
    });

  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
