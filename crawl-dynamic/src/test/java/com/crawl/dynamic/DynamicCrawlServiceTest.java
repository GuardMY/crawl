package com.crawl.dynamic;

import com.crawl.dynamic.service.DynamicCrawlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DynamicCrawlServiceTest {
    @Autowired
    private DynamicCrawlService service;

    @Test
    void testCrawlPage() {
        String url = "https://www.example.com";
        String html = service.crawlPage(url);
        assertNotNull(html);
        assertTrue(html.contains("Example Domain"));
    }

    @Test
    void testGetTitle() {
        String url = "https://www.example.com";
        String title = service.getTitle(url);
        assertNotNull(title);
        assertTrue(title.contains("Example Domain"));
    }

    @Test
    void testExtractLinks() {
        String url = "https://www.example.com";
        var links = service.extractLinks(url);
        assertNotNull(links);
        assertFalse(links.isEmpty());
    }

    @Test
    void testCrawlAndExtract() {
        String url = "https://www.example.com";
        var result = service.crawlAndExtract(url, 5);
        assertNotNull(result);
        assertNotNull(result.getTitle());
        assertNotNull(result.getLinks());
        assertNotNull(result.getHtml());
    }
}
