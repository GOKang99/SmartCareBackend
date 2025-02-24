package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.UserDTO;
import com.smartcarebackend.model.Guard;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.GuardRepository;
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

    @Autowired
    GuardRepository guardRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //생성자
    public UserServiceImpl(UserRepository userRepository, GuardRepository guardRepository) {
        this.userRepository = userRepository;
        this.guardRepository = guardRepository;
    }

//    @Override
//    public UserDTO getUserById(Long id) {
//        User user = userRepository.findById(id).orElseThrow();
//        return convertDTO(user);
//    }

    @Override
    public UserDTO getUserById(Long userId){
        // 유저 조회
        User user = userRepository.findById(userId).orElseThrow(()
        -> new RuntimeException("유저를 찾을 수 없음"));
        System.out.println("기버"+user.getGiver());
        System.out.println("가드"+user.getGuard());

        // 유저 정보 (ID, 이름, 이메일, 전화번호, 관계, 권한명 등)
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        String roleName = user.getRole().getRoleName().name();
        dto.setRoleName(roleName);

        // 권한 따라서 다르게 표시
        if ("ROLE_USER".equals(roleName)) {
            // 보호자니까 환자 정보 셋팅
            Guard guard = guardRepository.findByUser(user);
            if (guard != null && guard.getResident() != null) {
                Resident resident = guard.getResident();
                dto.setResidentId(resident.getResId());
                dto.setResidentName(resident.getResName());
                dto.setResidentImage(resident.getResImageAddress());

            }
        } else if ("ROLE_ADMIN".equals(roleName)) {
            // 관리자는 환자 정보 세팅 x
        }

        return dto;
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
