export default interface PaymentRequest {
    orderId: number;
    amount: number;
    currency: string;
    paymentProviderId: number;
    paymentStatusId: number;
    externalId: string;
    processedAt?: string | null;
}