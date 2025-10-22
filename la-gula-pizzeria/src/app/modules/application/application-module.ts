import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApplicationRoutingModule } from './application-routing-module';
import { MaterialModule } from '../../shared/modules/material-module.module';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ApplicationRoutingModule,
    MaterialModule
  ]
})
export class ApplicationModule { }
