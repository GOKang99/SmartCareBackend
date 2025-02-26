package com.smartcarebackend.dto;

import com.smartcarebackend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String relation;  // 환자와의 관계 (부모, 자녀 등)
    private String phone;  // 연락처
    private boolean agree;  // 약관 동의 여부
    private String roleName; // 역할(Role) 이름

    private Long residentId; //새 필드
    private String residentName; //새 필드
    private String residentImage; //새 필드

    private List<ResidentDTO> residents; // 입소자 정보 (연결된 입소자 리스트)
    private Role role;



    public UserDTO(Long userId, String username, String email, Role role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;

    }
}
