package com.crawl.dynamic.service;

import com.crawl.dynamic.util.DynamicCrawler;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.List;

@Service
public class DynamicCrawlService {
    public String crawlPage(String url) {
        DynamicCrawler crawler = null;
        try {
            crawler = new DynamicCrawler();
            return crawler.crawlPage(url);
        } finally {
            if (crawler != null) {
                crawler.close();
            }
        }
    }

    public String crawlPage(String url, int waitSeconds) {
        DynamicCrawler crawler = null;
        try {
            crawler = new DynamicCrawler();
            return crawler.crawlPage(url, Duration.ofSeconds(waitSeconds));
        } finally {
            if (crawler != null) {
                crawler.close();
            }
        }
    }

    public String crawlPage(String url, String waitForElementCss) {
        DynamicCrawler crawler = null;
        try {
            crawler = new DynamicCrawler();
            return crawler.crawlPage(url, waitForElementCss);
        } finally {
            if (crawler != null) {
                crawler.close();
            }
        }
    }

    public List<String> extractLinks(String url) {
        DynamicCrawler crawler = null;
        try {
            crawler = new DynamicCrawler();
            return crawler.extractLinks(url);
        } finally {
            if (crawler != null) {
                crawler.close();
            }
        }
    }

    public String getTitle(String url) {
        DynamicCrawler crawler = null;
        try {
            crawler = new DynamicCrawler();
            return crawler.getTitle(url);
        } finally {
            if (crawler != null) {
                crawler.close();
            }
        }
    }

    public CrawlResult crawlAndExtract(String url, int waitSeconds) {
        DynamicCrawler crawler = null;
        try {
            crawler = new DynamicCrawler();
            String html = crawler.crawlPage(url, Duration.ofSeconds(waitSeconds));
            String title = crawler.getTitle(url);
            List<String> links = crawler.extractLinks(url);

            CrawlResult result = new CrawlResult();
            result.setUrl(url);
            result.setTitle(title);
            result.setLinks(links);
            result.setHtml(html);

            return result;
        } finally {
            if (crawler != null) {
                crawler.close();
            }
        }
    }

    public CrawlResult crawlAndExtract(String url, String waitForElementCss) {
        DynamicCrawler crawler = null;
        try {
            crawler = new DynamicCrawler();
            String html = crawler.crawlPage(url, waitForElementCss);
            String title = crawler.getTitle(url);
            List<String> links = crawler.extractLinks(url);

            CrawlResult result = new CrawlResult();
            result.setUrl(url);
            result.setTitle(title);
            result.setLinks(links);
            result.setHtml(html);

            return result;
        } finally {
            if (crawler != null) {
                crawler.close();
            }
        }
    }

    @Data
    public static class CrawlResult {
        private String url;
        private String title;
        private List<String> links;
        private String html;
    }
}
