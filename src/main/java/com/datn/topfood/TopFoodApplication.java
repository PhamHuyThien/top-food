package com.datn.topfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TopFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopFoodApplication.class, args);
    }

}
