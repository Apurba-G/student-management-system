package com.sms.service;

import com.sms.dto.request.ChangePasswordRequest;
import com.sms.dto.request.LoginRequest;
import com.sms.dto.request.ResetPasswordRequest;
import com.sms.dto.response.AdminProfileResponse;
import com.sms.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
    AdminProfileResponse getCurrentAdmin(String username);
    void changePassword(String username, ChangePasswordRequest request);
    void resetPasswordWithRecoveryKey(ResetPasswordRequest request);
}
