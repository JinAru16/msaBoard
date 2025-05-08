package com.msa.board.community;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.NoIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class JasyptTest {
    @Value("${jasypt.encryptor.password}")
    String password;


    @Test
    public void testEncrypt() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        encryptor.setSaltGenerator(new RandomSaltGenerator());
        encryptor.setIvGenerator(new NoIvGenerator());

        String decrypted = encryptor.decrypt("pLkCYqJR4z4GDQ+hTBA/8X+6EwlSMAqk7ZY5WvrBw6nbVVdyWzTihZNAACBY6H6pcfQ02ErreLEhCkY1wR/3wEEAWVK0KAdGGDB0/jmP+wPu3u288RPktDAlSkunjzLquxUXTjJq1KyDPKPtuY+zSNtXsv1F0PwBe+yZe07DuCw+RUQa+RqtqBnLkobyy1JLPTL/5dqxaKg/+HhX3IOeIw==");
        System.out.println("복호화 결과: " + decrypted);
    }
}