export default interface OrderItemToppingRequest {
    toppingId: number;
    action: string;
    quantity: number;
    priceDelta: number;
    orderItemId: number;
}