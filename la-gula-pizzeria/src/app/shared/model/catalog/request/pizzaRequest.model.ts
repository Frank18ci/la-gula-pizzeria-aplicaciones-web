export default interface PizzaRequest {
    name: string;
    description?: string | null;
    basePrice: number;
    active: boolean;
    toppingIds?: number[];
}