import orderResponse from "./orderResponse.model";

export default interface OrderItemResponse {
    id: number;
    pizzaId: number;
    sizeId: number;
    doughTypeId: number;
    quantity: number;
    unitPrice: number;
    lineTotal: number;
    order: orderResponse;
    note? : string | null;
}