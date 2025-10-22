import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutPage } from './pages/layout-page/layout-page';
import { DashboardPage } from './pages/dashboard-page/dashboard-page';
import { PizzasPage } from './pages/pizzas-page/pizzas-page';
import { ToppingsPage } from './pages/toppings-page/toppings-page';
import { PaymentsPage } from './pages/payments-page/payments-page';
import { UsersPage } from './pages/users-page/users-page';
import { ClientsPage } from './pages/clients-page/clients-page';
import { DeliveriesPage } from './pages/deliveries-page/deliveries-page';
import { OrdersOrdeningPage } from './pages/orders-ordening-page/orders-ordening-page';
import { OrdersPaymentPage } from './pages/orders-payment-page/orders-payment-page';

const routes: Routes = [
  {
    path: '',
    component: LayoutPage,
    children: [
      {
        path: 'dashboard',
        component: DashboardPage
      },
      {
        path: 'pizzas',
        component: PizzasPage
      },
      {
        path: 'toppings',
        component: ToppingsPage
      },
      {
        path: 'payments',
        component: PaymentsPage
      },
      {
        path: 'users',
        component: UsersPage
      },
      {
        path: 'clients',
        component: ClientsPage
      },
      {
        path: 'deliveries',
        component: DeliveriesPage
      },
      {
        path: 'ordersOrdering',
        component: OrdersOrdeningPage
      },
      {
        path: 'ordersPayment',
        component: OrdersPaymentPage
      },
      {
        path: '**',
        redirectTo: 'dashboard'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
