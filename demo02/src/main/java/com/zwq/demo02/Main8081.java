package com.zwq.demo02;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zwq.demo02.mapper")
public class Main8081 {

    public static void main(String[] args) {
        SpringApplication.run(Main8081.class,args);
    }
}
