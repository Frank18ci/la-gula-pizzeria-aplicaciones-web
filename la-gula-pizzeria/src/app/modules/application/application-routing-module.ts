import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutPage } from './pages/layout-page/layout-page';
import { HomePage } from './pages/home-page/home-page';
import { DetailsPizzaComponent } from './components/details-pizza/details-pizza';



const routes: Routes = [
  {
    path: '',
    component: LayoutPage,
    children: [
      {path: 'home',component: HomePage},
      {path: '**',redirectTo: 'home'},
     { path: 'pizza-details/:id', component: DetailsPizzaComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule { }
