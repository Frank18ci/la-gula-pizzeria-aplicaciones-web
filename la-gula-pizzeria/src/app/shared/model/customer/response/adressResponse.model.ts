import CustomerResponse from "./customerResponse.model";

export default interface AdressResponse {
    id: number;
    customer: CustomerResponse;
    label: string;
    street: string;
    externalNumber: string;
    internalNumber: string;
    neighborhood: string;
    city: string;
    state: string;
    zipCode: string;
    country: string;
    reference: string;
    isDefault: boolean;
}