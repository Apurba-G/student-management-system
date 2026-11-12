export interface ResetPasswordRequest {
  username: string;
  recoveryKey: string;
  newPassword: string;
}
