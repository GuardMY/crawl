package com.crawl.task.controller;

import com.crawl.task.entity.CrawlTask;
import com.crawl.task.service.CrawlTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class CrawlTaskController {
    @Autowired
    private CrawlTaskService service;

    @GetMapping
    public List<CrawlTask> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrawlTask> getTaskById(@PathVariable Long id) {
        CrawlTask task = service.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public CrawlTask createTask(@RequestBody CrawlTask task) {
        return service.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrawlTask> updateTask(@PathVariable Long id, @RequestBody CrawlTask taskDetails) {
        CrawlTask updatedTask = service.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<CrawlTask> startTask(@PathVariable Long id) {
        CrawlTask startedTask = service.startTask(id);
        return ResponseEntity.ok(startedTask);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<CrawlTask> completeTask(@PathVariable Long id, @RequestParam String result) {
        CrawlTask completedTask = service.completeTask(id, result);
        return ResponseEntity.ok(completedTask);
    }

    @PostMapping("/{id}/fail")
    public ResponseEntity<CrawlTask> failTask(@PathVariable Long id, @RequestParam String errorMessage) {
        CrawlTask failedTask = service.failTask(id, errorMessage);
        return ResponseEntity.ok(failedTask);
    }

    @Autowired
    private com.crawl.task.service.TaskExecutionService taskExecutionService;

    @PostMapping("/{id}/execute")
    public ResponseEntity<Void> executeTask(@PathVariable Long id) {
        taskExecutionService.executeTask(id);
        return ResponseEntity.accepted().build();
    }
}
