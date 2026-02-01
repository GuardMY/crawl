package com.crawl.task.service;

import com.crawl.task.entity.Permission;
import java.util.List;

public interface PermissionService {
    Permission createPermission(Permission permission);
    Permission getPermissionById(Long id);
    List<Permission> getAllPermissions();
    Permission updatePermission(Long id, Permission permissionDetails);
    void deletePermission(Long id);
    List<Permission> getPermissionsByRoleId(Long roleId);
    List<Permission> getPermissionsByUserId(Long userId);
    boolean hasPermission(Long userId, String permissionKey);
    void assignPermissionToRole(Long roleId, Long permissionId);
    void removePermissionFromRole(Long roleId, Long permissionId);
}
