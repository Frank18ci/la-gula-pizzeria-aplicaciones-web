import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutPage } from './pages/layout-page/layout-page';
import { HomePage } from './pages/home-page/home-page';
import { DetailsPizzaComponent } from './pages/details-pizza/details-pizza';
import { PizzasPageComponent } from './pages/pizzas-page/pizzas-page';



const routes: Routes = [
  {
    path: '',
    component: LayoutPage,
    children: [
      {path: 'home',component: HomePage},
      {path: 'pizza-details/:id', component: DetailsPizzaComponent },
      {path: 'pizzas', component: PizzasPageComponent},
      {path: '**',redirectTo: 'home'},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule { }
