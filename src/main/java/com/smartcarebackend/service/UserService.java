package com.smartcarebackend.service;

import com.smartcarebackend.dto.GuardDTO;
import com.smartcarebackend.dto.UserDTO;
import com.smartcarebackend.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    UserDTO getUserById(Long id);

    User findByUsername(String username);
}
