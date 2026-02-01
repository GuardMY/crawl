package com.crawl.task.service;

import com.crawl.task.entity.Role;
import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    Role getRoleById(Long id);
    List<Role> getAllRoles();
    Role updateRole(Long id, Role roleDetails);
    void deleteRole(Long id);
    List<Role> getRolesByUserId(Long userId);
}
