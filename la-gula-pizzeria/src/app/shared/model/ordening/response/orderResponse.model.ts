export default interface orderResponse {
    id: number;
    orderNumber: string;
    customerId: number;
    addressId: number;
    status: string;
    deliveryMethod: string;
    notes: string;
    subtotal: number;
    tax: number;
    deliveryFee: number;
    discountTotal: number;
    total: number;
    paymentStatus: string;
    placedAt: string;
}
