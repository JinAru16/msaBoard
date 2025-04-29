package com.msa.board;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"com.msa.board", "com.msa.common"})
@EntityScan(basePackages = {"com.msa.board", "com.msa.common.entity"})
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BoardApplication.class);
        //app.addListeners(new EarlyDecryptListener());  // 🔐 복호화 리스너 등록
        app.run(args);
    }

}
