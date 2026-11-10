package com.sms.controller;

import com.sms.dto.request.ChangePasswordRequest;
import com.sms.dto.request.LoginRequest;
import com.sms.dto.response.AdminProfileResponse;
import com.sms.dto.response.LoginResponse;
import com.sms.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // Admin login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid
            @RequestBody
            LoginRequest request

    ){
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Get currently authenticated admin profile
    @GetMapping("/me")
    public ResponseEntity<AdminProfileResponse> getCurrentAdmin(Authentication authentication){
        String username = authentication.getName();
        AdminProfileResponse response =
                authService.getCurrentAdmin(username);


        return ResponseEntity.ok(response);
    }




    //Change admin password

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(Authentication authentication, @Valid @RequestBody ChangePasswordRequest request){
        String username = authentication.getName();
        authService.changePassword(username, request);

        return ResponseEntity.noContent().build();
    }


}
