package com.crawl.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crawl.task.entity.Permission;
import com.crawl.task.entity.RolePermission;
import com.crawl.task.entity.UserRole;
import com.crawl.task.mapper.PermissionMapper;
import com.crawl.task.mapper.RolePermissionMapper;
import com.crawl.task.mapper.UserRoleMapper;
import com.crawl.task.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Permission createPermission(Permission permission) {
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        permissionMapper.insert(permission);
        return permission;
    }

    @Override
    public Permission getPermissionById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionMapper.selectList(null);
    }

    @Override
    public Permission updatePermission(Long id, Permission permissionDetails) {
        Permission permission = permissionMapper.selectById(id);
        if (permission == null) {
            throw new RuntimeException("权限不存在");
        }

        permission.setPermissionName(permissionDetails.getPermissionName());
        permission.setPermissionKey(permissionDetails.getPermissionKey());
        permission.setDescription(permissionDetails.getDescription());
        permission.setUrl(permissionDetails.getUrl());
        permission.setMethod(permissionDetails.getMethod());
        permission.setUpdateTime(LocalDateTime.now());

        permissionMapper.updateById(permission);
        return permission;
    }

    @Override
    public void deletePermission(Long id) {
        // 检查权限是否被角色关联
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_id", id);
        if (rolePermissionMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("权限已被角色关联，无法删除");
        }

        permissionMapper.deleteById(id);
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        // 先查询角色关联的权限ID
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(wrapper);

        // 提取权限ID列表
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 根据权限ID列表查询权限
        if (permissionIds.isEmpty()) {
            return List.of();
        }

        return permissionMapper.selectBatchIds(permissionIds);
    }

    @Override
    public List<Permission> getPermissionsByUserId(Long userId) {
        // 先查询用户关联的角色ID
        QueryWrapper<UserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleWrapper);

        // 提取角色ID列表
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return List.of();
        }

        // 查询所有角色关联的权限ID
        QueryWrapper<RolePermission> rolePermissionWrapper = new QueryWrapper<>();
        rolePermissionWrapper.in("role_id", roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionWrapper);

        // 提取权限ID列表（去重）
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .distinct()
                .collect(Collectors.toList());

        // 根据权限ID列表查询权限
        if (permissionIds.isEmpty()) {
            return List.of();
        }

        return permissionMapper.selectBatchIds(permissionIds);
    }

    @Override
    public boolean hasPermission(Long userId, String permissionKey) {
        // 获取用户的所有权限
        List<Permission> permissions = getPermissionsByUserId(userId);
        // 检查是否包含指定权限
        return permissions.stream()
                .anyMatch(permission -> permission.getPermissionKey().equals(permissionKey));
    }

    @Override
    public void assignPermissionToRole(Long roleId, Long permissionId) {
        // 检查角色-权限关联是否已存在
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId)
                .eq("permission_id", permissionId);
        if (rolePermissionMapper.selectOne(wrapper) != null) {
            return; // 已存在，无需重复添加
        }

        // 创建新的角色-权限关联
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(permissionId);
        rolePermissionMapper.insert(rolePermission);
    }

    @Override
    public void removePermissionFromRole(Long roleId, Long permissionId) {
        // 删除角色-权限关联
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId)
                .eq("permission_id", permissionId);
        rolePermissionMapper.delete(wrapper);
    }
}
