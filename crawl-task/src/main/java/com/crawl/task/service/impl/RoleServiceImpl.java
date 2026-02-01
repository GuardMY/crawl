package com.crawl.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crawl.task.entity.Role;
import com.crawl.task.entity.UserRole;
import com.crawl.task.mapper.RoleMapper;
import com.crawl.task.mapper.UserRoleMapper;
import com.crawl.task.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Role createRole(Role role) {
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insert(role);
        return role;
    }

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectList(null);
    }

    @Override
    public Role updateRole(Long id, Role roleDetails) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }

        role.setRoleName(roleDetails.getRoleName());
        role.setDescription(roleDetails.getDescription());
        role.setUpdateTime(LocalDateTime.now());

        roleMapper.updateById(role);
        return role;
    }

    @Override
    public void deleteRole(Long id) {
        // 检查角色是否被用户关联
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        if (userRoleMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("角色已被用户关联，无法删除");
        }

        roleMapper.deleteById(id);
    }

    @Override
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

        return roleMapper.selectBatchIds(roleIds);
    }
}
