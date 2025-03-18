package com.msa.board;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BoardApplication.class);
        //app.addListeners(new EarlyDecryptListener());  // 🔐 복호화 리스너 등록
        app.run(args);
    }

}
