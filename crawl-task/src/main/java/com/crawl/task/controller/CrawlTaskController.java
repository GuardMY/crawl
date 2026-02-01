package com.crawl.task.controller;

import com.crawl.task.entity.CrawlTask;
import com.crawl.task.entity.User;
import com.crawl.task.service.CrawlTaskService;
import com.crawl.task.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class CrawlTaskController {
    @Autowired
    private CrawlTaskService service;

    @Autowired
    private PermissionService permissionService;

    private Long getCurrentUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    @GetMapping
    public List<CrawlTask> getAllTasks() {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:list")) {
            throw new RuntimeException("无权限查看任务列表");
        }
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrawlTask> getTaskById(@PathVariable Long id) {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:view")) {
            throw new RuntimeException("无权限查看任务详情");
        }
        CrawlTask task = service.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public CrawlTask createTask(@RequestBody CrawlTask task) {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:create")) {
            throw new RuntimeException("无权限创建任务");
        }
        return service.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrawlTask> updateTask(@PathVariable Long id, @RequestBody CrawlTask taskDetails) {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:update")) {
            throw new RuntimeException("无权限更新任务");
        }
        CrawlTask updatedTask = service.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:delete")) {
            throw new RuntimeException("无权限删除任务");
        }
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<CrawlTask> startTask(@PathVariable Long id) {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:start")) {
            throw new RuntimeException("无权限启动任务");
        }
        CrawlTask startedTask = service.startTask(id);
        return ResponseEntity.ok(startedTask);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<CrawlTask> completeTask(@PathVariable Long id, @RequestParam String result) {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:complete")) {
            throw new RuntimeException("无权限完成任务");
        }
        CrawlTask completedTask = service.completeTask(id, result);
        return ResponseEntity.ok(completedTask);
    }

    @PostMapping("/{id}/fail")
    public ResponseEntity<CrawlTask> failTask(@PathVariable Long id, @RequestParam String errorMessage) {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:fail")) {
            throw new RuntimeException("无权限标记任务失败");
        }
        CrawlTask failedTask = service.failTask(id, errorMessage);
        return ResponseEntity.ok(failedTask);
    }

    @Autowired
    private com.crawl.task.service.TaskExecutionService taskExecutionService;

    @PostMapping("/{id}/execute")
    public ResponseEntity<Void> executeTask(@PathVariable Long id) {
        // 检查权限
        if (!permissionService.hasPermission(getCurrentUserId(), "task:execute")) {
            throw new RuntimeException("无权限执行任务");
        }
        taskExecutionService.executeTask(id);
        return ResponseEntity.accepted().build();
    }
}
