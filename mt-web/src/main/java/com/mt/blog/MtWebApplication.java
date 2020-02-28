package com.mt.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.mt.blog.mapper")
@EnableCaching
@EnableAsync
public class MtWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MtWebApplication.class, args);
    }

}
