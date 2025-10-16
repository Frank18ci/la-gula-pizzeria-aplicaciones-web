export default interface DeliveryRequest {
    orderId: number;
    addressId: number;
    method: DeliveryMethod;
    status: DeliveryStatus;
    driverName: string;
    driverPhone: string;
    instructions: string;
}

export enum DeliveryMethod {
    DELIVERY,
    PICKUP
}

export enum DeliveryStatus {
    pending,
    assigned,
    en_route,
    completed,
    canceled
}