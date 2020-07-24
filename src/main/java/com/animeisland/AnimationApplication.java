package com.animeisland;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.animeisland.mapper")
@SpringBootApplication
public class AnimationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimationApplication.class, args);
    }

}
