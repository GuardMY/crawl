package com.crawl.task;

import com.crawl.task.entity.CrawlTask;
import com.crawl.task.service.CrawlTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrawlTaskServiceTest {
    @Autowired
    private CrawlTaskService service;

    @Test
    void testCreateTask() {
        CrawlTask task = new CrawlTask();
        task.setTaskName("Test Task");
        task.setUrl("https://www.example.com");
        task.setCrawlType("static");

        CrawlTask createdTask = service.createTask(task);
        assertNotNull(createdTask.getId());
        assertEquals("Test Task", createdTask.getTaskName());
        assertEquals("pending", createdTask.getStatus());
    }

    @Test
    void testGetAllTasks() {
        List<CrawlTask> tasks = service.getAllTasks();
        assertNotNull(tasks);
    }

    @Test
    void testUpdateTask() {
        CrawlTask task = new CrawlTask();
        task.setTaskName("Test Task");
        task.setUrl("https://www.example.com");
        task.setCrawlType("static");
        CrawlTask createdTask = service.createTask(task);

        createdTask.setTaskName("Updated Test Task");
        createdTask.setStatus("running");
        CrawlTask updatedTask = service.updateTask(createdTask.getId(), createdTask);

        assertEquals("Updated Test Task", updatedTask.getTaskName());
        assertEquals("running", updatedTask.getStatus());
    }

    @Test
    void testDeleteTask() {
        CrawlTask task = new CrawlTask();
        task.setTaskName("Test Task");
        task.setUrl("https://www.example.com");
        task.setCrawlType("static");
        CrawlTask createdTask = service.createTask(task);

        service.deleteTask(createdTask.getId());
        assertNull(service.getTaskById(createdTask.getId()));
    }
}
