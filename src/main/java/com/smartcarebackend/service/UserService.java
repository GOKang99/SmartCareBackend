package com.smartcarebackend.service;

import com.smartcarebackend.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long userId);
}
