package com.crawl.task.service;

import com.crawl.task.entity.CrawlTask;
import com.crawl.task.mapper.CrawlTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CrawlTaskService {
    @Autowired
    private CrawlTaskMapper mapper;

    public List<CrawlTask> getAllTasks() {
        return mapper.selectList(null);
    }

    public CrawlTask getTaskById(Long id) {
        CrawlTask task = mapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        return task;
    }

    public CrawlTask createTask(CrawlTask task) {
        task.setCreateTime(LocalDateTime.now());
        task.setStatus("pending");
        mapper.insert(task);
        return task;
    }

    public CrawlTask updateTask(Long id, CrawlTask taskDetails) {
        CrawlTask task = mapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        task.setTaskName(taskDetails.getTaskName());
        task.setUrl(taskDetails.getUrl());
        task.setCrawlType(taskDetails.getCrawlType());
        task.setStatus(taskDetails.getStatus());
        task.setResult(taskDetails.getResult());
        if ("running".equals(taskDetails.getStatus())) {
            task.setStartTime(LocalDateTime.now());
        }
        if ("completed".equals(taskDetails.getStatus()) || "failed".equals(taskDetails.getStatus())) {
            task.setEndTime(LocalDateTime.now());
        }
        mapper.updateById(task);
        return task;
    }

    public void deleteTask(Long id) {
        mapper.deleteById(id);
    }

    public CrawlTask startTask(Long id) {
        CrawlTask task = mapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        task.setStatus("running");
        task.setStartTime(LocalDateTime.now());
        mapper.updateById(task);
        return task;
    }

    public CrawlTask completeTask(Long id, String result) {
        CrawlTask task = mapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        task.setStatus("completed");
        task.setEndTime(LocalDateTime.now());
        task.setResult(result);
        mapper.updateById(task);
        return task;
    }

    public CrawlTask failTask(Long id, String errorMessage) {
        CrawlTask task = mapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        task.setStatus("failed");
        task.setEndTime(LocalDateTime.now());
        task.setResult(errorMessage);
        mapper.updateById(task);
        return task;
    }
}
