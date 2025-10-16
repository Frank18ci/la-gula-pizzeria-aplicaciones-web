export default interface OrderItemRequest {
    pizzaId: number;
    sizeId: number;
    doughTypeId: number;
    quantity: number;
    unitPrice: number;
    lineTotal: number;
    orderId: number;
    note? : string | null;
}