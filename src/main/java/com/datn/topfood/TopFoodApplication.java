package com.datn.topfood;

import com.datn.topfood.configs.FileUploadConfig;
import com.datn.topfood.data.seeders.BatchSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({FileUploadConfig.class})
public class TopFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopFoodApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        BatchSeeder.seed();
    }
}
