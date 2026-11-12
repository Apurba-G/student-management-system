package com.sms.config;

import com.sms.entity.Admin;
import com.sms.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminDataInitializer.class);

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default-username:admin}")
    private String defaultUsername;

    @Value("${app.admin.default-password:admin123}")
    private String defaultPassword;

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .username(defaultUsername)
                    .passwordHash(passwordEncoder.encode(defaultPassword))
                    .role("ADMIN")
                    .enabled(true)
                    .tokenVersion(0L)
                    .build();
            adminRepository.save(admin);
            log.info("Default admin user initialized successfully: username='{}'", defaultUsername);
        } else {
            log.info("Admin accounts already exist (count={}); skipping seeding.", adminRepository.count());
        }
    }
}
