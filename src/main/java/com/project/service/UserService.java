package com.project.service;

import com.project.model.User;

public interface UserService {
    void save(User user);

    void updateMail(String username, String mail);

    void updatePhone(String username, String phone);

    User findByUsername(String username);
}
