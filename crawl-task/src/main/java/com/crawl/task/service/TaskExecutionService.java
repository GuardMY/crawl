package com.crawl.task.service;

import com.crawl.task.client.DynamicCrawlClient;
import com.crawl.task.client.StaticCrawlClient;
import com.crawl.task.entity.CrawlTask;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutionService {
    @Resource
    private StaticCrawlClient staticCrawlClient;

    @Resource
    private DynamicCrawlClient dynamicCrawlClient;

    @Resource
    private CrawlTaskService crawlTaskService;

    public void executeTask(Long taskId) {
        CrawlTask task = crawlTaskService.getTaskById(taskId);

        try {
            // 更新任务状态为运行中
            crawlTaskService.startTask(taskId);

            String result;
            if ("static".equals(task.getCrawlType())) {
                // 调用静态爬取服务
                var crawlResult = staticCrawlClient.crawlPage(task.getUrl());
                String title = (String) crawlResult.get("title");
                var links = (java.util.List<?>) crawlResult.get("links");
                result = "Title: " + title + "\nLinks: " + (links != null ? links.size() : 0) + " links found";
            } else if ("dynamic".equals(task.getCrawlType())) {
                // 调用动态爬取服务
                var crawlResult = dynamicCrawlClient.crawlPage(task.getUrl(), 5, null);
                String title = (String) crawlResult.get("title");
                var links = (java.util.List<?>) crawlResult.get("links");
                result = "Title: " + title + "\nLinks: " + (links != null ? links.size() : 0) + " links found";
            } else {
                throw new RuntimeException("Invalid crawl type: " + task.getCrawlType());
            }

            // 更新任务状态为完成
            crawlTaskService.completeTask(taskId, result);
        } catch (Exception e) {
            // 更新任务状态为失败
            crawlTaskService.failTask(taskId, "Error executing task: " + e.getMessage());
        }
    }
}
