package com.crawl.task.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crawl.task.entity.CrawlTask;
import com.crawl.task.mapper.CrawlTaskMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CrawlTaskService extends ServiceImpl<CrawlTaskMapper, CrawlTask> {

    public List<CrawlTask> getAllTasks() {
        return baseMapper.selectList(null);
    }

    public CrawlTask getTaskById(Long id) {
        CrawlTask task = baseMapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        return task;
    }

    public CrawlTask createTask(CrawlTask task) {
        task.setCreateTime(LocalDateTime.now());
        task.setStatus("pending");
        baseMapper.insert(task);
        return task;
    }

    public void deleteTask(Long id) {
        baseMapper.deleteById(id);
    }

    public CrawlTask updateTask(Long id, CrawlTask taskDetails) {
        CrawlTask task = baseMapper.selectById(id);
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
        baseMapper.updateById(task);
        return task;
    }

    public CrawlTask startTask(Long id) {
        CrawlTask task = baseMapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        task.setStatus("running");
        task.setStartTime(LocalDateTime.now());
        baseMapper.updateById(task);
        return task;
    }

    public CrawlTask completeTask(Long id, String result) {
        CrawlTask task = baseMapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        task.setStatus("completed");
        task.setEndTime(LocalDateTime.now());
        task.setResult(result);
        baseMapper.updateById(task);
        return task;
    }

    public CrawlTask failTask(Long id, String errorMessage) {
        CrawlTask task = baseMapper.selectById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id " + id);
        }
        task.setStatus("failed");
        task.setEndTime(LocalDateTime.now());
        task.setResult(errorMessage);
        baseMapper.updateById(task);
        return task;
    }
}
