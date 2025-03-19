package com.msa.board.email;


import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {

    private final JavaMailSender mailSender;

    @GetMapping("/field")
    public void everything() {
        Arrays.stream(mailSender.getClass().getFields()).forEach(field -> {
            System.out.println(field.getName());
        });
    }

}
