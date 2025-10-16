import OrderItemResponse from "./orderItemResponse.model";

export default interface OrderItemToppingResponse {
    id: number;
    toppingId: number;
    action: string;
    quantity: number;
    priceDelta: number;
    orderItem: OrderItemResponse;
}