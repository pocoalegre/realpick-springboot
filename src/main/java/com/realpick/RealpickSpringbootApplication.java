package com.realpick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.realpick.dao")
public class RealpickSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealpickSpringbootApplication.class, args);
    }

}
