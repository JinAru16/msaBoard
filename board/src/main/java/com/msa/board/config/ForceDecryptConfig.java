package com.msa.board.config;

import jakarta.annotation.PostConstruct;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ForceDecryptConfig {

    private final StringEncryptor encryptor;

    @Value("${spring.datasource.url}")
    private String encryptedUrl;

    @Value("${spring.datasource.username}")
    private String encryptedUsername;

    @Value("${spring.datasource.password}")
    private String encryptedPassword;

    public ForceDecryptConfig(@Qualifier("jasyptStringEncryptor") StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @PostConstruct
    public void decryptAndInject() {
        try {
            String url = decrypt(encryptedUrl);
            String username = decrypt(encryptedUsername);
            String password = decrypt(encryptedPassword);

            System.out.println("🔐 복호화된 JDBC URL: " + url);

            // HikariCP가 이 값을 쓰도록 시스템 프로퍼티 강제 주입
            System.setProperty("spring.datasource.url", url);
            System.setProperty("spring.datasource.username", username);
            System.setProperty("spring.datasource.password", password);

        } catch (Exception e) {
            System.out.println("❌ 복호화 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String decrypt(String value) {
        if (value != null && value.startsWith("ENC(") && value.endsWith(")")) {
            String encrypted = value.substring(4, value.length() - 1);
            return encryptor.decrypt(encrypted);
        }
        return value;
    }
}