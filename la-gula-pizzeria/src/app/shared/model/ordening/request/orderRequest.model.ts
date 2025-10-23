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
    pending = 'pending',
    in_progress = 'in_progress',
    completed = 'completed',
    canceled = 'canceled'
}
export enum DeliveryMethod {
    DELIVERY = 'DELIVERY',
    PICKUP = 'PICKUP'
}
export enum PaymentStatus {
    unpaid = 'unpaid',
    paid = 'paid'
}
