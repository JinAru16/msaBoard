package com.msa.board.email;

import com.msa.board.email.domain.MailSecurity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;


@Getter
public class CommonEmail {

  JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

  @Builder
  public CommonEmail(String host, String port, String username, String password, MailSecurity mailSecurity) {
      mailSender.setHost(host);
      mailSender.setPort(Integer.parseInt(port));
      mailSender.setUsername(username);
      mailSender.setPassword(password);
      mailSender.setJavaMailProperties(getMailProperties(mailSecurity));
      mailSender.setDefaultEncoding("UTF-8");
  }

    private Properties getMailProperties(MailSecurity mailSecurity) {
        Properties pt = new Properties();
        switch (mailSecurity) {
            case SMPT:
                pt.put("mail.smtp.socketFactory.port", 25);
                pt.put("mail.smtp.auth", false);
                pt.put("mail.smtp.starttls.enable", false);
                pt.put("mail.smtp.starttls.required", false);
                break;
            case STARTTLS:
                pt.put("mail.smtp.socketFactory.port", 587);
                pt.put("mail.smtp.auth", false);
                pt.put("mail.smtp.starttls.enable", true);
                pt.put("mail.smtp.starttls.required", true);
            case SSL:
                pt.put("mail.smtp.socketFactory.port", 465);
                pt.put("mail.smtp.auth", true);
                pt.put("mail.smtp.starttls.enable", true);
                pt.put("mail.smtp.starttls.required", true);
                pt.put("mail.smtp.socketFactory.fallback",true);
                pt.put("mail.smtp.ssl.enable", "true"); // SSL 설정의 간편 버전
        }
        return pt;
    }



}
