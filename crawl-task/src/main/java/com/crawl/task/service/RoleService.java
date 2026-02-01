package com.crawl.task.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crawl.task.entity.Role;
import com.crawl.task.entity.UserRole;
import com.crawl.task.mapper.RoleMapper;
import com.crawl.task.mapper.UserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private UserRoleMapper userRoleMapper;

    public Role createRole(Role role) {
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(role);
        return role;
    }

    public Role getRoleById(Long id) {
        return baseMapper.selectById(id);
    }

    public List<Role> getAllRoles() {
        return baseMapper.selectList(null);
    }

    public Role updateRole(Long id, Role roleDetails) {
        Role role = baseMapper.selectById(id);
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }

        role.setRoleName(roleDetails.getRoleName());
        role.setDescription(roleDetails.getDescription());
        role.setUpdateTime(LocalDateTime.now());

        baseMapper.updateById(role);
        return role;
    }

    public void deleteRole(Long id) {
        // 检查角色是否被用户关联
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        if (userRoleMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("角色已被用户关联，无法删除");
        }

        baseMapper.deleteById(id);
    }

    public List<Role> getRolesByUserId(Long userId) {
        // 先查询用户关联的角色ID
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(wrapper);

        // 提取角色ID列表
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // 根据角色ID列表查询角色
        if (roleIds.isEmpty()) {
            return List.of();
        }

        return baseMapper.selectBatchIds(roleIds);
    }
}
