package com.crawl.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.crawl.task.mapper")
public class CrawlTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrawlTaskApplication.class, args);
    }
}
