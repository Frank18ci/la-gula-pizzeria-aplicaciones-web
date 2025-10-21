import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import UserResponse from '../../../../shared/model/user/response/userResponse.model';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from '../../../../shared/services/user/user-service';
import { RoleService } from '../../../../shared/services/user/role-service';
import { UserRoleService } from '../../../../shared/services/user/user-role-service';
import UserRoleResponse from '../../../../shared/model/user/response/userRoleResponse.model';

@Component({
  selector: 'app-users-page',
  imports: [MatTableModule, MatPaginatorModule, CommonModule, MatIconModule],
  templateUrl: './users-page.html',
  styleUrl: './users-page.css'
})
export class UsersPage implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['Id', 'Email', 'Nombre Completo', 'Telefono', 'Estado', 'Roles', 'Acciones'];
  dataSource = new MatTableDataSource<UserResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  userRoles: UserRoleResponse[] = [];
  constructor(
    private userService: UserService,
    private userRoleService: UserRoleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadUsers();
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }
  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users: UserResponse[]) => {
        this.dataSource.data = users;
        this.loadUserRoles();
      },
      error: (error) => {
        console.error('Error loading users:', error);
      }
    });
  }
  loadUserRoles(): void {
  this.userRoleService.getAllUserRoles().subscribe({
    next: (roles) => {
      this.userRoles = roles;

      this.dataSource.data = this.dataSource.data.map(user => {
        const userRoles = roles
          .filter(role => role.user.id === user.id)
          .map(role => role.role.name)
          .join(', ');
        return { ...user, roles: userRoles };
      });
    },
    error: (error) => console.error('Error loading user roles:', error)
    });
  }  

  openDialog(user?: UserResponse): void {
    // Implementation for opening user dialog goes here
  }
  viewUserDetails(user: UserResponse): void {
    // Implementation for viewing user details goes here
  }
  deleteUser(userId: number): void {
    if(confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
      this.userService.deleteUser(userId).subscribe({
        next: () => {
          this.loadUsers();
        },
        error: (error) => {
          console.error('Error deleting user:', error);
        }
      });
    }
  }
  buscarUserPorNombre(nombre: string): void {
    this.userService.getUsersByName(nombre).subscribe({
      next: (users: UserResponse[]) => {
        this.dataSource.data = users;
      },
      error: (error) => {
        console.error('Error searching users by name:', error);
      }
    });
  }
  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
