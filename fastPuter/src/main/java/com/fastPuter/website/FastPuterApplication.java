/*
* Main program to run
* */
package com.fastPuter.website;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.fastPuter.website.dao")
@SpringBootApplication
public class FastPuterApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastPuterApplication.class, args);
    }
}
