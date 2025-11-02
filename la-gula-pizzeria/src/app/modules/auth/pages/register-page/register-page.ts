import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import UserRequest from '../../../../shared/model/user/request/userRequest.model';
import { AuthService } from '../../../../shared/services/auth/auth-service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register-page',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './register-page.html',
  styleUrl: './register-page.css'
})
export class RegisterPage implements OnInit {
  form!: FormGroup;
  userRequest: UserRequest = {
    email: '',
    password: '',
    fullName: '',
    phone: null,
    status: ''
  }
  constructor(
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService
  ) {

  }
  ngOnInit(): void {
    this.form = this.fb.group({
      email: [this.userRequest.email, [Validators.email, Validators.required]],
      password: [this.userRequest.password, [Validators.required, Validators.minLength(6)]],
      fullName: [this.userRequest.fullName, [Validators.required, Validators.minLength(3)]],
    });
  }
  register(): void {
    if (this.form.invalid) {
      this.toastr.error('Por favor, completa todos los campos requeridos', 'Error al Registrarse');
      return;
    }
    this.userRequest = { ...this.form.value };
    this.userRequest.status = 'ACTIVE';
    this.userRequest.phone = null;
    this.authService.register(this.userRequest).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        this.router.navigate(['/auth/login']);
      },
      error: (error) => {
        console.error('Registration failed', error);
      }
    });
  }


}
