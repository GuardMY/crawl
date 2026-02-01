package com.crawl.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.crawl.task.mapper")
@Slf4j
public class CrawlTaskApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(CrawlTaskApplication.class, args);
        } catch (Exception e) {
            log.error("应用启动失败", e);
        }
    }
}
