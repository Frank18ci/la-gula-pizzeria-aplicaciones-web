import { Injectable } from '@angular/core';
import Carrito from '../../model/carrito/carrito';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private static readonly cartKey = 'shopping_cart_gula_pizzeria';

  getCart(): Carrito[] {
    const cartJson = localStorage.getItem(CarritoService.cartKey);
    return cartJson ? JSON.parse(cartJson) : [];
  }
  saveItemToCart(item: Carrito): void {
    const cart = this.getCart();
    cart.push(item);
    this.saveCart(cart);
  }
  saveCart(cart: Carrito[]): void {
    localStorage.setItem(CarritoService.cartKey, JSON.stringify(cart));
  }
  clearCart(): void {
    localStorage.removeItem(CarritoService.cartKey);
  }
  getCartItemCount(): number {
    const cart = this.getCart();
    return cart.length;
  }
  getNextId(): number {
    const cart = this.getCart();
    if (cart.length === 0) return 1;
    return Math.max(...cart.map(item => item.id)) + 1;
  }
  deleteItemById(id: number): void {
    let cart = this.getCart();
    cart = cart.filter(item => item.id !== id);
    this.saveCart(cart);
  }
}
