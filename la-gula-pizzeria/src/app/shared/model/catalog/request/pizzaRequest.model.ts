export default interface PizzaRequest {
    name: string;
    description?: string | null;
    basePrice: number;
    image: string;
    active: boolean;
    toppingIds?: number[];
}