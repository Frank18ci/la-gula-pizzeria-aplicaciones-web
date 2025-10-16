export default interface DeliveryResponse {
    id: number;
    orderId: number;
    addressId: number;
    method: string;
    status: string;
    driverName: string;
    driverPhone: string;
    instructions: string;
}