import DoughTypeResponse from "../catalog/response/doughTypeResponse.model";
import PizzaResponse from "../catalog/response/pizzaResponse.model";
import SizeResponse from "../catalog/response/SizeResponse.model";
import ToppingResponse from "../catalog/response/toppingResponse.model";

export default interface CarritoDto {
    id: number;
    pizza: PizzaResponse;
    quantity: number;
    doughType: DoughTypeResponse;
    size: SizeResponse;
    toppings: ToppingResponseCarrito[];
    subtotal: number;
}
export interface ToppingResponseCarrito {
    topping: ToppingResponse;
    quantity: number;
}