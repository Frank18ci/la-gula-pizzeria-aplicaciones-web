import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { AdminRoutingModule } from "../../../admin/admin-routing-module";
import { ApplicationRoutingModule } from '../../application-routing-module';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [MatIconModule, AdminRoutingModule,RouterModule],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header implements OnInit {

  ngOnInit() {
    // Initialization logic can go here
  }

  images = [
    {images: '/icons/logoo.jpeg'},
  ];
}
