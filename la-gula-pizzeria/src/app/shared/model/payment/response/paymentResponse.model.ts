import OrderResponse from "./orderPaymentResponse.model";
import PaymentProviderResponse from "./paymentProviderResponse.model";
import PaymentStatusResponse from "./paymentStatusResponse.model";

export default interface PaymentResponse {
    id: number;
    order: OrderResponse;
    amount: number;
    currency: string;
    paymentProvider: PaymentProviderResponse;
    paymentStatus: PaymentStatusResponse;
    externalId: string;
    processedAt?: string | null;
    createdAt: string;
    updatedAt: string;
}