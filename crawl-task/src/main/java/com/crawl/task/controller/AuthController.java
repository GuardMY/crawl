package com.crawl.task.controller;

import com.crawl.task.entity.User;
import com.crawl.task.service.UserService;
import com.crawl.task.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = userService.login(username, password);
        String token = JwtUtils.generateToken(user.getUsername(), user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/userinfo")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String authorization) {
        // 从Authorization头中提取token
        String token = authorization.substring(7); // 去掉"Bearer "前缀
        Long userId = JwtUtils.getUserIdFromToken(token);

        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}
