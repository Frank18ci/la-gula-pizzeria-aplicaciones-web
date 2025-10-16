export default interface AdressRequest{
    customerId: number;
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