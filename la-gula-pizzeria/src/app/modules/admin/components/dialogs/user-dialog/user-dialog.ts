import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import UserRequest from '../../../../../shared/model/user/request/userRequest.model';
import UserResponse from '../../../../../shared/model/user/response/userResponse.model';
import { RoleService } from '../../../../../shared/services/user/role-service';
import { UserRoleService } from '../../../../../shared/services/user/user-role-service';
import RoleResponse from '../../../../../shared/model/user/response/roleResponse.model';
import UserRoleRequest from '../../../../../shared/model/user/request/userRoleRequest.model';
import { UserService } from '../../../../../shared/services/user/user-service';

@Component({
  selector: 'app-user-dialog',
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
  templateUrl: './user-dialog.html',
  styleUrl: './user-dialog.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserDialog implements OnInit {
  form!: FormGroup;
  userRequest: UserRequest = {
    email: '',
    password: '',
    fullName: '',
    phone: null,
    status: ''
  }
  userId?: number;

  roles: RoleResponse[] = [];
  rolesElegidos: number[] = [];
  constructor(
    private dialogRef: MatDialogRef<UserDialog>,
    @Inject(MAT_DIALOG_DATA) public data: UserResponse,
    private fb: FormBuilder,
    private cdRef: ChangeDetectorRef,
    private roleService: RoleService,
    private userRoleService: UserRoleService,
    private userService: UserService
  ) {
    if (data) {
      this.userRequest = {
        email: data.email ?? '',
        password: '',
        fullName: data.fullName ?? '',
        phone: data.phone ?? null,
        status: data.status ?? ''
      }
      this.userId = data.id ?? undefined;
    }
    
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      email: [this.userRequest.email, [Validators.required, Validators.email]],
      password: [this.userRequest.password, [Validators.required, Validators.minLength(6)]],
      fullName: [this.userRequest.fullName, [Validators.required]],
      phone: [this.userRequest.phone],
      status: [this.userRequest.status, [Validators.required]],
      roles: []
    });
    this.roleService.getAllRoles().subscribe({
      next: (roles) => {
        this.roles = roles;
        if (this.userId) {
          this.userRoleService.getAllUserRolesByUserId(this.userId).subscribe({
            next: (userRoles) => {
              this.rolesElegidos = userRoles.map(ur => ur.role.id);
              this.form.patchValue({ roles: this.rolesElegidos });
              this.cdRef.detectChanges();
            },
            error: (error) => {
              console.error('Error loading user roles by user id:', error);
            }
          });
        }
        this.cdRef.detectChanges();
      },
      error: (error) => {
        console.error('Error loading roles:', error);
      }
    });
  }

  saveUser(): void {
    if (this.form.valid) {
      const userToSave: UserRequest = {
        email: this.form.value.email,
        password: this.form.value.password,
        fullName: this.form.value.fullName,
        phone: this.form.value.phone,
        status: this.form.value.status
      };
      if (!this.userId) {
        this.userService.saveUser(userToSave).subscribe({
        next: (savedUser: UserResponse) => {
          const selectedRoleIds: number[] = this.form.value.roles;
          selectedRoleIds.forEach(roleId => {
            const userRoleRequests: UserRoleRequest = {
              userId: savedUser.id,
              roleId: roleId
            };
            this.userRoleService.saveUserRole(userRoleRequests).subscribe({
              next: () => {
                console.log('User role saved successfully');
              },
              error: (error) => {
                console.error('Error saving user role:', error);
              }
            });
          });
          this.dialogRef.close(true);
        },
        error: (error) => {
          console.error('Error saving user:', error);
        }
      });
      } else {
        this.userService.updateUser(this.userId, userToSave).subscribe({
          next: () => {
            const selectedRoleIds: number[] = this.form.value.roles;

            this.userRoleService.getAllUserRolesByUserId(this.userId!).subscribe({
              next: (existingUserRoles) => {
                const existingRoleIds = existingUserRoles.map(ur => ur.role.id);
                const rolesToAdd = selectedRoleIds.filter(roleId => !existingRoleIds.includes(roleId));
                const rolesToRemove = existingRoleIds.filter(roleId => !selectedRoleIds.includes(roleId));

                rolesToAdd.forEach(roleId => {
                  const userRoleRequests: UserRoleRequest = {
                    userId: this.userId!,
                    roleId: roleId
                  };
                  this.userRoleService.saveUserRole(userRoleRequests).subscribe({
                    next: () => {
                      console.log('User role added successfully');
                    },
                    error: (error) => {
                      console.error('Error adding user role:', error);
                    }
                  });
                });

                rolesToRemove.forEach(roleId => {
                  this.userRoleService.deleteUserRole(this.userId!, roleId).subscribe({
                    next: () => {
                      console.log('User role removed successfully');
                    },
                    error: (error) => {
                      console.error('Error removing user role:', error);
                    }
                  });
                });
              },
              error: (error) => {
                console.error('Error fetching existing user roles:', error);
              }
            });

          this.dialogRef.close(true);
          },
          error: (error) => {
            console.error('Error updating user:', error);
          }
        });
      }
    }
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
