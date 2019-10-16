package com.colourful.chat_with_u;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.colourful.chat_with_u.dao")
@SpringBootApplication
public class ChatWithUApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatWithUApplication.class, args);
    }

}
