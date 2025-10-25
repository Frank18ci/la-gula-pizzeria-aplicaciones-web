import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { UserService } from '../../../../shared/services/user/user-service';
import { UserRoleService } from '../../../../shared/services/user/user-role-service';
import UserResponse from '../../../../shared/model/user/response/userResponse.model';
import UserRoleResponse from '../../../../shared/model/user/response/userRoleResponse.model';
import RoleResponse from '../../../../shared/model/user/response/roleResponse.model';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../shared/modules/material-module.module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-perfil-page',
  imports: [CommonModule,MaterialModule,FormsModule],
  templateUrl: './perfil-page.html',
  styleUrl: './perfil-page.css'
})
export class PerfilPage implements OnInit{
  user: UserResponse = {
     id: 1,
    email: 'admin@example.com',
    password: 'hashed_password_admin',
    fullName: 'Admin User',
    phone: '1234567890',
    status: 'active',
    createdAt: '2023-01-01T00:00:00Z',
    updatedAt: ''
  };

  isFavoriteHome = false;
isFavoriteWork = false;

  roles: RoleResponse[] = [
    {id: 1, name: 'admin', description: 'Administrador del sistema', createdAt: '2025-10-09T12:57:18', updatedAt: '2025-10-09T12:57:18'},
    {id: 2, name: 'cliente', description: 'Cliente final', createdAt: '2025-10-09T12:57:18', updatedAt: '2025-10-09T12:57:18'}
  ];

  constructor(
    private userService: UserService,
    private userRoleService: UserRoleService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loadUserData();
    this.loadRoles();
  }

  loadUserData() {
    this.userService.getUserById(1).subscribe({
      next: data => {
        this.user = data;
        this.cdr.detectChanges();
        console.log(this.user);
      },
      error: err => console.error(err)
    });
  }
  loadRoles() {
    this.userRoleService.getAllUserRolesByUserId(1).subscribe({
      next: data => {
        this.roles = data.map(ur => ur.role);
        this.cdr.detectChanges();
        console.log(this.roles);
      },
      error: err => console.error(err)
    });
  }

  onAddAddress(){
    console.log('Add new address...');

  }
toggleFavorite(type: string) {
  if (type === 'home') {
    this.isFavoriteHome = !this.isFavoriteHome;
  } else if (type === 'work') {
    this.isFavoriteWork = !this.isFavoriteWork;
  }
}

 
}
