package com.example.milanarestoran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MilanaRestoranApplication {

    public static void main(String[] args) {
        SpringApplication.run(MilanaRestoranApplication.class, args);
    }

}
