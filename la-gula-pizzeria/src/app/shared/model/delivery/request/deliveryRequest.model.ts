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
    DELIVERY = 'DELIVERY',
    PICKUP = 'PICKUP'
}

export enum DeliveryStatus {
    pending = 'pending',
    assigned = 'assigned',
    en_route = 'en_route',
    completed = 'completed',
    canceled = 'canceled'
}