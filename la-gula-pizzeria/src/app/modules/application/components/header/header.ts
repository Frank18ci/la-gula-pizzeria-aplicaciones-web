import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { AdminRoutingModule } from "../../../admin/admin-routing-module";

@Component({
  selector: 'app-header',
  imports: [MatIconModule, AdminRoutingModule],
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
