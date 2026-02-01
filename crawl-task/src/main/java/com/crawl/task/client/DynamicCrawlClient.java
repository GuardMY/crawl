package com.crawl.task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@FeignClient(name = "crawl-dynamic", url = "http://localhost:8082")
public interface DynamicCrawlClient {
    @GetMapping("/api/dynamic-crawl/crawl-and-extract")
    Map<String, Object> crawlPage(
            @RequestParam("url") String url,
            @RequestParam(value = "waitSeconds", required = false) Integer waitSeconds,
            @RequestParam(value = "waitForElementCss", required = false) String waitForElementCss
    );
}
