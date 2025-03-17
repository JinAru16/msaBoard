package com.msa.board.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.NoIvGenerator;
import org.jasypt.salt.ZeroSaltGenerator;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

public class EarlyDecryptListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment env = event.getEnvironment();

        String password = env.getProperty("jasypt.encryptor.password");
        if (password == null) {
            System.out.println("❌ Jasypt password is missing. 복호화 불가");
            return;
        }

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        encryptor.setSaltGenerator(new ZeroSaltGenerator());
        encryptor.setIvGenerator(new NoIvGenerator());

        try {
            String encryptedUrl = env.getProperty("spring.datasource.url");
            String encryptedUsername = env.getProperty("spring.datasource.username");
            String encryptedPassword = env.getProperty("spring.datasource.password");

//            String url = decrypt(encryptor, encryptedUrl);
//            String username = decrypt(encryptor, encryptedUsername);
//            String dbPassword = decrypt(encryptor, encryptedPassword);

            String aasdf = "pLkCYqJR4z4GDQ+hTBA/8X+6EwlSMAqk7ZY5WvrBw6nbVVdyWzTihZNAACBY6H6pcfQ02ErreLEhCkY1wR/3wEEAWVK0KAdGGDB0/jmP+wPu3u288RPktDAlSkunjzLquxUXTjJq1KyDPKPtuY+zSNtXsv1F0PwBe+yZe07DuCw+RUQa+RqtqBnLkobyy1JLPTL/5dqxaKg/+HhX3IOeIw==";

            String decrypt = encryptor.decrypt(aasdf);

            System.out.println("decrypt = " + decrypt);

//            System.setProperty("spring.datasource.url", url);
//            System.setProperty("spring.datasource.username", username);
//            System.setProperty("spring.datasource.password", dbPassword);

     //       System.out.println("🔐 [EarlyDecrypt] JDBC URL: " + url);

        } catch (Exception e) {
            System.out.println("❌ [EarlyDecrypt] 복호화 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String decrypt(StandardPBEStringEncryptor encryptor, String value) {
        if (value != null && value.startsWith("ENC(") && value.endsWith(")")) {
            String enc = value.substring(4, value.length() - 1);
            return encryptor.decrypt(enc);
        }
        return value;
    }
}