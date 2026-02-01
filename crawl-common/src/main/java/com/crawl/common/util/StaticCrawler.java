package com.crawl.common.util;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticCrawler {
    public static String crawlPage(String url) throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed to crawl page: HTTP " + statusCode);
                }
                return EntityUtils.toString(response.getEntity());
            }
        }
    }

    public static Document parseHtml(String html) {
        return Jsoup.parse(html);
    }

    public static List<String> extractLinks(String html, String baseUrl) {
        Document doc = Jsoup.parse(html, baseUrl);
        Elements links = doc.select("a[href]");
        List<String> linkList = new ArrayList<>();
        for (Element link : links) {
            String href = link.attr("abs:href");
            if (!href.isEmpty()) {
                linkList.add(href);
            }
        }
        return linkList;
    }

    public static String extractText(String html) {
        Document doc = Jsoup.parse(html);
        return doc.text();
    }

    public static String extractTitle(String html) {
        Document doc = Jsoup.parse(html);
        Element titleElement = doc.selectFirst("title");
        return titleElement != null ? titleElement.text() : "";
    }
}
