package com.example.for_testdemo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.for_testdemo1.Mapper")
public class ForTestdemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(ForTestdemo1Application.class, args);
    }
}
