export default interface AuthResponse {
    id: number;
    token: string;
    username: string;
    fullName: string;
    roles: string[];
}