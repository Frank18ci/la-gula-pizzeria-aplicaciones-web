import RoleResponse from "./roleResponse.model";
import UserResponse from "./userResponse.model";

export default interface UserRoleResponse {
    user: UserResponse;
    role: RoleResponse;
}