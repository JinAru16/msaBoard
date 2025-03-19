package com.msa.board.email;


import com.msa.board.email.domain.MailSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {

    @Value("${SMTP.username}")
    private String smtpUsername;

    @Value("${SMTP.password}")
    private String smtpPassword;

    @Value("${SMTP.port}")
    private String smtpPort;

    @Bean
    public JavaMailSender CoreMailSender(){
        CommonEmail commonEmail = CommonEmail.builder()
                .host("smtp.gmail.com")
                .port(smtpPort)
                .username(smtpUsername)
                .password(smtpPassword)
                .mailSecurity(MailSecurity.STARTTLS)
                .build();

        return commonEmail.getMailSender();
    }
}
