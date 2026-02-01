package com.crawl.task.service;

import com.crawl.task.entity.User;
import java.util.List;

public interface UserService {
    User register(User user);
    User login(String username, String password);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
}
