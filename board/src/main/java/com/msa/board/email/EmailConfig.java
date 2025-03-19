package com.msa.board.email;


import com.msa.board.email.domain.MailSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender CoreMailSender(){
        CommonEmail commonEmail = CommonEmail.builder()
                .host("smtp.gmail.com")
                .port("345")
                .username("admin@gmail.com")
                .password("password")
                .mailSecurity(MailSecurity.STARTTLS)
                .build();

        return commonEmail.getMailSender();
    }
}
