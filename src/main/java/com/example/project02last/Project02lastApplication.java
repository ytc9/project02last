package com.example.project02last;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc //这里是防止swagger报错
public class Project02lastApplication {
    public static void main(String[] args) {
        SpringApplication.run(Project02lastApplication.class, args);
    }


}
