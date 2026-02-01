package com.crawl.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@TableName("role")
@Data
public class Role {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String roleName;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
