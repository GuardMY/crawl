package com.crawl.common.controller;

import com.crawl.common.service.StaticCrawlService;
import lombok.Data;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/static-crawl")
public class StaticCrawlController {
    @Autowired
    private StaticCrawlService service;

    @GetMapping("/crawl")
    public ResponseEntity<StaticCrawlService.CrawlResult> crawlPage(@RequestParam String url) {
        try {
            StaticCrawlService.CrawlResult result = service.crawlAndExtract(url);
            return ResponseEntity.ok(result);
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to crawl page: " + e.getMessage());
        }
    }

    @PostMapping("/extract-title")
    public ResponseEntity<String> extractTitle(@RequestBody String html) {
        String title = service.extractTitle(html);
        return ResponseEntity.ok(title);
    }

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractText(@RequestBody String html) {
        String text = service.extractText(html);
        return ResponseEntity.ok(text);
    }

    @PostMapping("/extract-links")
    public ResponseEntity<?> extractLinks(@RequestBody ExtractLinksRequest request) {
        try {
            return ResponseEntity.ok(service.extractLinks(request.getHtml(), request.getBaseUrl()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error extracting links: " + e.getMessage());
        }
    }

    @Data
    public static class ExtractLinksRequest {
        private String html;
        private String baseUrl;
    }
}
