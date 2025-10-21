import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../shared/services/auth/auth-service';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import AuthRequest from '../../../../shared/model/auth/AuthRequest.model';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css'
})
export class LoginPage implements OnInit {
  form!: FormGroup;
  authRequest : AuthRequest = {
    username: '',
    password: ''
  }
  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {}
  login(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.authRequest = { ...this.form.value };
    this.authService.login(this.authRequest).subscribe({
      next: (response) => {
        // Handle successful login
        this.router.navigate(['/application/home']);
      },
      error: (error) => {
        // Handle login error
        console.error('Login failed', error);
      }
    });
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

}
