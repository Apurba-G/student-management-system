package com.sms.service.impl;

import com.sms.dto.request.ChangePasswordRequest;
import com.sms.dto.request.LoginRequest;
import com.sms.dto.response.AdminProfileResponse;
import com.sms.dto.response.LoginResponse;
import com.sms.entity.Admin;
import com.sms.mapper.AdminMapper;
import com.sms.repository.AdminRepository;
import com.sms.security.JwtService;
import com.sms.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AdminMapper adminMapper;

    @org.springframework.beans.factory.annotation.Value("${app.admin.recovery-key:sms_admin_recovery_key_2026}")
    private String systemRecoveryKey;


    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "তুমি কে হে বাল"
                        )
                );

        if (!admin.isEnabled()
                || !passwordEncoder.matches(
                request.getPassword(),
                admin.getPasswordHash()
        )) {

            throw new BadCredentialsException(
                    "তুমি কে হে বাল"
            );
        }

        String token = jwtService.generateToken(admin.getUsername(), admin.getTokenVersion());

        return new LoginResponse(
                token,
                "Bearer",
                admin.getId(),
                admin.getUsername(),
                admin.getRole()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AdminProfileResponse getCurrentAdmin(String username) {

        Admin admin = adminRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("আপনকা থিক চিন্তে পারলাম না") );
        return adminMapper.toProfileResponse(admin);
    }

    @Override
    @Transactional

    public void changePassword(
            String username, ChangePasswordRequest request
            ) {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("অ্যাডমিন অ্যাকাউন্ট পাওয়া যায়নি"));

        // Verify current password
        if(!passwordEncoder.matches(request.getCurrentPassword(), admin.getPasswordHash()))
        {
            throw new BadCredentialsException("Current password is incorrect");

        }
        // Verify new password confirmation
        if (!request.getNewPassword().equals(
                request.getConfirmNewPassword()
        )) {
            throw new IllegalArgumentException("New password and confirmation password do not match");
        }

        // Prevent reusing the current password
        if(passwordEncoder.matches(request.getNewPassword(),admin.getPasswordHash())) {
            throw new IllegalArgumentException("new password must be different from the current password");
        }

        // BCrypt accepts a maximum of 72 input bytes
        int passwordBytes = request.getNewPassword().getBytes(StandardCharsets.UTF_8).length;
        if(passwordBytes > 72) {
            throw new IllegalArgumentException("password connot exceeed 72 UTF-8 bytes");
        }

        // Hash the password
        admin.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        // Invalidate previously issued JWTs
        admin.setTokenVersion(admin.getTokenVersion() + 1);
// Save updated admin
        adminRepository.save(admin);
    }

    @Override
    @Transactional
    public void resetPasswordWithRecoveryKey(com.sms.dto.request.ResetPasswordRequest request) {
        if (request.getRecoveryKey() == null || !request.getRecoveryKey().trim().equals(systemRecoveryKey.trim())) {
            throw new BadCredentialsException("Invalid emergency recovery key");
        }

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Admin account not found with username: " + request.getUsername()));

        int passwordBytes = request.getNewPassword().getBytes(StandardCharsets.UTF_8).length;
        if (passwordBytes > 72) {
            throw new IllegalArgumentException("Password cannot exceed 72 UTF-8 bytes");
        }

        admin.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        admin.setTokenVersion(admin.getTokenVersion() + 1);
        adminRepository.save(admin);
    }
}
