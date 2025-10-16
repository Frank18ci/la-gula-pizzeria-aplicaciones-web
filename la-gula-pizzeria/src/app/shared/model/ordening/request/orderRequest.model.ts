export default interface orderRequest {
    orderNumber: string;
    customerId: number;
    addressId: number;
    status: OrderStatus;
    deliveryMethod: DeliveryMethod;
    notes: string;
    subtotal: number;
    tax: number;
    deliveryFee: number;
    discountTotal: number;
    total: number;
    paymentStatus: PaymentStatus;
    placedAt: string;
}
export enum OrderStatus {
    pending,
    in_progress,
    completed,
    canceled
}
export enum DeliveryMethod {
    DELIVERY,
    PICKUP
}
export enum PaymentStatus {
    unpaid,
    paid
}
