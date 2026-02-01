package com.crawl.task.controller;

import com.crawl.task.entity.Permission;
import com.crawl.task.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.createPermission(permission);
        return ResponseEntity.ok(createdPermission);
    }

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permissionDetails) {
        Permission updatedPermission = permissionService.updatePermission(id, permissionDetails);
        return ResponseEntity.ok(updatedPermission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<Permission>> getPermissionsByRoleId(@PathVariable Long roleId) {
        List<Permission> permissions = permissionService.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Permission>> getPermissionsByUserId(@PathVariable Long userId) {
        List<Permission> permissions = permissionService.getPermissionsByUserId(userId);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignPermissionToRole(@RequestParam Long roleId, @RequestParam Long permissionId) {
        permissionService.assignPermissionToRole(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removePermissionFromRole(@RequestParam Long roleId, @RequestParam Long permissionId) {
        permissionService.removePermissionFromRole(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkPermission(@RequestParam Long userId, @RequestParam String permissionKey) {
        boolean hasPermission = permissionService.hasPermission(userId, permissionKey);
        return ResponseEntity.ok(hasPermission);
    }
}
