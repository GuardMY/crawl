package com.crawl.common.service;

import com.crawl.common.util.StaticCrawler;
import lombok.Data;
import org.apache.hc.core5.http.ParseException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class StaticCrawlService {
    public String crawlPage(String url) throws IOException, ParseException {
        return StaticCrawler.crawlPage(url);
    }

    public Document parseHtml(String html) {
        return StaticCrawler.parseHtml(html);
    }

    public List<String> extractLinks(String html, String baseUrl) {
        return StaticCrawler.extractLinks(html, baseUrl);
    }

    public String extractText(String html) {
        return StaticCrawler.extractText(html);
    }

    public String extractTitle(String html) {
        return StaticCrawler.extractTitle(html);
    }

    public CrawlResult crawlAndExtract(String url) throws IOException, ParseException {
        String html = crawlPage(url);
        String title = extractTitle(html);
        String text = extractText(html);
        List<String> links = extractLinks(html, url);

        CrawlResult result = new CrawlResult();
        result.setUrl(url);
        result.setTitle(title);
        result.setText(text);
        result.setLinks(links);
        result.setHtml(html);

        return result;
    }

    @Data
    public static class CrawlResult {
        private String url;
        private String title;
        private String text;
        private List<String> links;
        private String html;
    }
}
