export default interface SizeResponse{
    id: number;
    name: string;
    diameterCm?: number | null;
    priceMultiplier: number;
    active: boolean;
}