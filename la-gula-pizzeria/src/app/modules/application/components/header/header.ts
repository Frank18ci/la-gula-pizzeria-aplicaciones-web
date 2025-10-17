import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { AdminRoutingModule } from "../../../admin/admin-routing-module";

@Component({
  selector: 'app-header',
  imports: [MatIconModule, AdminRoutingModule],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header {

}
