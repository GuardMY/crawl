package com.crawl.staticcrawl;

import com.crawl.staticcrawl.service.StaticCrawlService;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StaticCrawlServiceTest {
    @Autowired
    private StaticCrawlService service;

    @Test
    void testCrawlPage() throws IOException, ParseException {
        String url = "https://www.example.com";
        String html = service.crawlPage(url);
        assertNotNull(html);
        assertTrue(html.contains("Example Domain"));
    }

    @Test
    void testExtractTitle() throws IOException, ParseException {
        String url = "https://www.example.com";
        String html = service.crawlPage(url);
        String title = service.extractTitle(html);
        assertNotNull(title);
        assertTrue(title.contains("Example Domain"));
    }

    @Test
    void testExtractLinks() throws IOException, ParseException {
        String url = "https://www.example.com";
        String html = service.crawlPage(url);
        var links = service.extractLinks(html, url);
        assertNotNull(links);
        assertFalse(links.isEmpty());
    }

    @Test
    void testCrawlAndExtract() throws IOException, ParseException {
        String url = "https://www.example.com";
        var result = service.crawlAndExtract(url);
        assertNotNull(result);
        assertNotNull(result.getTitle());
        assertNotNull(result.getText());
        assertNotNull(result.getLinks());
        assertNotNull(result.getHtml());
    }
}
