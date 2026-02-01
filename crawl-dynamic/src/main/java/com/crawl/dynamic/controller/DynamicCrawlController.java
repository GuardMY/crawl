package com.crawl.dynamic.controller;

import com.crawl.dynamic.service.DynamicCrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dynamic-crawl")
public class DynamicCrawlController {
    @Autowired
    private DynamicCrawlService service;

    @GetMapping("/crawl")
    public ResponseEntity<String> crawlPage(@RequestParam String url) {
        try {
            String html = service.crawlPage(url);
            return ResponseEntity.ok(html);
        } catch (Exception e) {
            throw new RuntimeException("Failed to crawl page: " + e.getMessage());
        }
    }

    @GetMapping("/crawl-with-wait")
    public ResponseEntity<String> crawlPageWithWait(@RequestParam String url, @RequestParam int waitSeconds) {
        try {
            String html = service.crawlPage(url, waitSeconds);
            return ResponseEntity.ok(html);
        } catch (Exception e) {
            throw new RuntimeException("Failed to crawl page: " + e.getMessage());
        }
    }

    @GetMapping("/crawl-with-element")
    public ResponseEntity<String> crawlPageWithElement(@RequestParam String url, @RequestParam String waitForElementCss) {
        try {
            String html = service.crawlPage(url, waitForElementCss);
            return ResponseEntity.ok(html);
        } catch (Exception e) {
            throw new RuntimeException("Failed to crawl page: " + e.getMessage());
        }
    }

    @GetMapping("/extract-links")
    public ResponseEntity<?> extractLinks(@RequestParam String url) {
        try {
            return ResponseEntity.ok(service.extractLinks(url));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error extracting links: " + e.getMessage());
        }
    }

    @GetMapping("/get-title")
    public ResponseEntity<String> getTitle(@RequestParam String url) {
        try {
            String title = service.getTitle(url);
            return ResponseEntity.ok(title);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get title: " + e.getMessage());
        }
    }

    @GetMapping("/crawl-and-extract")
    public ResponseEntity<DynamicCrawlService.CrawlResult> crawlAndExtract(@RequestParam String url, @RequestParam(required = false) Integer waitSeconds, @RequestParam(required = false) String waitForElementCss) {
        try {
            DynamicCrawlService.CrawlResult result;
            if (waitForElementCss != null) {
                result = service.crawlAndExtract(url, waitForElementCss);
            } else if (waitSeconds != null) {
                result = service.crawlAndExtract(url, waitSeconds);
            } else {
                result = service.crawlAndExtract(url, 5); // 默认等待5秒
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to crawl and extract: " + e.getMessage());
        }
    }
}
