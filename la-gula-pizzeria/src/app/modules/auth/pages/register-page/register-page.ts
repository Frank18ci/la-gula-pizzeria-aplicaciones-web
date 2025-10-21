import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import UserRequest from '../../../../shared/model/user/request/userRequest.model';
import { AuthService } from '../../../../shared/services/auth/auth-service';

@Component({
  selector: 'app-register-page',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './register-page.html',
  styleUrl: './register-page.css'
})
export class RegisterPage implements OnInit{
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
    private authService: AuthService
  ){

  }
  ngOnInit(): void {
    this.form = this.fb.group({
      email: [this.userRequest.email, [Validators.email, Validators.required]],
      password: [this.userRequest.password, [Validators.required, Validators.minLength(6)]],
      fullName: [this.userRequest.fullName, [Validators.required, Validators.minLength(3)]],
    });
  }
  register(): void {
    if (this.form.valid) {
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


}
