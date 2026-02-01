package com.crawl.task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@FeignClient(name = "crawl-static", url = "http://localhost:8081")
public interface StaticCrawlClient {
    @GetMapping("/api/static-crawl/crawl")
    Map<String, Object> crawlPage(@RequestParam("url") String url);
}
