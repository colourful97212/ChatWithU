package com.colourful.chat_with_u;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.colourful.chat_with_u.dao")
@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ChatWithUApplication extends SpringBootServletInitializer
{

    public static void main(String[] args) {
        SpringApplication.run(ChatWithUApplication.class, args);
    }

    @Override//为了打包SpringBoot项目
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        return builder.sources(this.getClass());
    }
}
