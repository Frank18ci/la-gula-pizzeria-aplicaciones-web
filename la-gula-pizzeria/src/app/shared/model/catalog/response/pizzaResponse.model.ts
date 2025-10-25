import SizeResponse from "./SizeResponse.model";
import ToppingResponse from "./toppingResponse.model";


export default interface PizzaResponse {
    id: number;
    name: string;
    description?: string | null;
    basePrice: number;
    image: string;
    active: boolean;
    toppings?: ToppingResponse[];
    sizes?: SizeResponse[];
}