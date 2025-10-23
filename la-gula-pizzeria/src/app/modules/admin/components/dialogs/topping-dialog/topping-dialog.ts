import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import ToppingRequest from '../../../../../shared/model/catalog/request/toppingRequest.model';
import ToppingResponse from '../../../../../shared/model/catalog/response/toppingResponse.model';
import { ToppingService } from '../../../../../shared/services/catalog/topping-service';

@Component({
  selector: 'app-topping-dialog',
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
  templateUrl: './topping-dialog.html',
  styleUrl: './topping-dialog.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ToppingDialog implements OnInit {
  form!: FormGroup;
  toppingRequest: ToppingRequest = {
    name: '',
    image: '',
    basePrice: 0,
    category: '',
    isVegetarian: false,
    active: true
  }
  toppingId?: number;
  nombreImagen: string = '';
  constructor(
    private dialogRef: MatDialogRef<ToppingDialog>,
    @Inject(MAT_DIALOG_DATA) public data: ToppingResponse,
    private fb: FormBuilder,
    private toppingService: ToppingService,
    private cdRef: ChangeDetectorRef
  ) {
    if(data) {
      this.toppingRequest = {
        name: data.name ?? '',
        image: data.image ?? '',
        basePrice: data.basePrice ?? 0,
        category: data.category ?? '',
        isVegetarian: data.isVegetarian ?? false,
        active: data.active ?? true
      }
      this.toppingId = data.id ?? undefined;
      this.nombreImagen = data.image ?? '';
    }
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      name: [this.toppingRequest.name, [Validators.required]],
      basePrice: [this.toppingRequest.basePrice, [Validators.required]],
      category: [this.toppingRequest.category, [Validators.required]],
      isVegetarian: [this.toppingRequest.isVegetarian, [Validators.required]],
      active: [this.toppingRequest.active, [Validators.required]],
    });
  }

  selectedImageFile: File | null = null;
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const firstFile: File | null = input.files && input.files.length ? input.files[0] : null;
    this.selectedImageFile = firstFile;
    this.nombreImagen = firstFile ? firstFile.name : '';
  }
  
  saveTopping(): void {
    if(this.form.invalid) {
      return;
    }
    const formValues = this.form.value;
    const fd = new FormData();

    Object.entries(formValues).forEach(([key, value]) => {
      if (key === 'image' || value === null || value === undefined) return;
      fd.append(key, value.toString());
    });
    if(this.selectedImageFile) {
      fd.append('image', this.selectedImageFile);
    }
    const request$ = this.toppingId
      ? this.toppingService.updateTopping(this.toppingId, fd)
      : this.toppingService.saveTopping(fd);
    request$.subscribe({
      next: () => {
        this.dialogRef.close(true);
      },
      error: (error) => {
        console.error('Error saving topping:', error);
      }
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
