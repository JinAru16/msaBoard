package com.msa.board.email;


import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.sun.org.apache.xalan.internal.res.XSLMessages.createMessage;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {

    private final JavaMailSender mailSender;

    @GetMapping("/field")
    public void everything() throws MessagingException, UnsupportedEncodingException {

        MimeMessage  message = mailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, "wjdcksndr11@naver.com");//보내는 대상
        message.setSubject("이메일 인증 테스트");//제목
        message.setText("test email", "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jinaru0131","nevWebAdmin"));//보내는 사람


        mailSender.send(message);
    }

}
