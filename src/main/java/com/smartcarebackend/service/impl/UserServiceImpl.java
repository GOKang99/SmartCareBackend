package com.smartcarebackend.service.impl;

import com.smartcarebackend.dto.UserDTO;
import com.smartcarebackend.model.Guard;
import com.smartcarebackend.model.Resident;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.GuardRepository;
import com.smartcarebackend.repositories.UserRepository;
import com.smartcarebackend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GuardRepository guardRepository;

    public UserServiceImpl(UserRepository userRepository, GuardRepository guardRepository) {
        this.userRepository = userRepository;
        this.guardRepository = guardRepository;
    }

    @Override
    public UserDTO getUserById(Long userId){
        // 유저 조회
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("유저를 찾을 수 없음"));

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
                // 사진이 있으면 표시 없으면 기본
                String image = (resident.getResImageAddress() == null || resident.getResImageAddress().isEmpty())
                        ? "https://via.placeholder.com/150" : resident.getResImageAddress();
                dto.setResidentImage(image);
            }
        } else if ("ROLE_ADMIN".equals(roleName)) {
            // 관리자는 환자 정보 세팅 x
        }

        return dto;
    }

}
