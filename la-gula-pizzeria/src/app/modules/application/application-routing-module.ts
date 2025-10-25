import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutPage } from './pages/layout-page/layout-page';
import { HomePage } from './pages/home-page/home-page';
import { DetailsPizzaComponent } from './pages/details-pizza/details-pizza';
import { PizzasPageComponent } from './pages/pizzas-page/pizzas-page';
import { PerfilPage } from './pages/perfil-page/perfil-page';
import { ContactPage } from './pages/contact-page/contact-page';
import { CarritoPage } from './pages/carrito-page/carrito-page';



const routes: Routes = [
  {
    path: '',
    component: LayoutPage,
    children: [
      {path: 'home',component: HomePage},
      {path: 'pizza-details/:id', component: DetailsPizzaComponent },
      {path: 'pizzas', component: PizzasPageComponent},
      {path: 'profile', component: PerfilPage},
      {path: 'contacto', component: ContactPage},
      {path: 'carrito', component: CarritoPage},
      {path: '**',redirectTo: 'home'},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule { }
