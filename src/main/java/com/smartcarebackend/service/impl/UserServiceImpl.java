package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.UserDTO;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.RoleRepository;
import com.smartcarebackend.repositories.UserRepository;
import com.smartcarebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return convertDTO(user);
    }

    private UserDTO convertDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    //유저이름으로 User 엔티티 찾기
    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(()->new RuntimeException("유저를 찾을 수 없습니다"));
    }
}
