export default interface Carrito {
    id: number;
    pizzaId: number;
    quantity: number;
    doughTypeId: number;
    sizeId: number;
    toppings: ToppingCarrito[];
}
export interface ToppingCarrito {
    toppingId: number;
    quantity: number;
}