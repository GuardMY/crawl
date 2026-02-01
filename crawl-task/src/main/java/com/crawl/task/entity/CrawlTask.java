package com.crawl.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@TableName("crawl_task")
@Data
public class CrawlTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskName;
    private String url;
    private String crawlType; // static or dynamic
    private String status; // pending, running, completed, failed
    private LocalDateTime createTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String result;
}
