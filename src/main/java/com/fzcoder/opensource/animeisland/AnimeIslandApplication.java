package com.fzcoder.opensource.animeisland;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.fzcoder.opensource.animeisland.mapper"})
public class AnimeIslandApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeIslandApplication.class, args);
    }

}
