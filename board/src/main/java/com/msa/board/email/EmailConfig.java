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
                .host("smtp.gmail.co")
                .port("465")
                .username("jinaru0131")
                .password("Skrktkzl123!")
                .mailSecurity(MailSecurity.STARTTLS)
                .build();

        return commonEmail.getMailSender();
    }
}
