package com.crawl.task.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("role_permission")
@Data
public class RolePermission {
    @TableId
    private Long id;
    private Long roleId;
    private Long permissionId;
}
