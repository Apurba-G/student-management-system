package com.sms.mapper;

import com.sms.dto.response.AdminProfileResponse;
import com.sms.entity.Admin;

import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    public AdminProfileResponse toProfileResponse(Admin admin) {
        return new AdminProfileResponse(
                admin.getId(),admin.getUsername(),admin.getRole(), admin.isEnabled(), admin.getCreatedAt(),admin.getUpdatedAt()
        );
    }
}
