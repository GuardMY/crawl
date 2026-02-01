package com.crawl.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@TableName("permission")
@Data
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String permissionName;
    private String permissionKey;
    private String description;
    private String url;
    private String method;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
