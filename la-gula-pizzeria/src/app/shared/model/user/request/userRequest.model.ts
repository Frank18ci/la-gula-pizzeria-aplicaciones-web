export default interface UserRequest {
    email: string;
    password: string;
    fullName: string;
    phone: string | null;
    status: string;
}