import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import AuthResponse from '../../../../shared/model/auth/AuthResponse.model';
import { AdminRoutingModule } from "../../../admin/admin-routing-module";

@Component({
  selector: 'app-header',
  imports: [MatIconModule, AdminRoutingModule, RouterModule],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header implements OnInit {


  constructor(
    private cookieService: CookieService,
    private router: Router
  ) { }
  user !: AuthResponse;
  ngOnInit(): void {
    this.user = JSON.parse(this.cookieService.get('user') || '{}');
  }
  images = [
    { images: '/icons/logoo.jpeg' },
  ];
  isAdmin(): boolean {
    return this.user?.roles?.includes('admin');
  }
  isLoggedIn(): boolean {
    return !!this.user?.token;
  }
  logout(): void {
    this.cookieService.delete('user');
    this.router.navigate(['/auth/login']);
  }
  obtainUserName(): string {
    return this.user?.fullName || 'Invitado';
  }

}
