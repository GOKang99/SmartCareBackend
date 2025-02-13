package com.smartcarebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId; // 유저 PK
    private String username; // 유저 이름
    private String email;
    private String relation;  // 환자와의 관계 (부모, 자녀 등)
    private String phone;  // 연락처
    private boolean agree;  // 약관 동의 여부
    private String roleName; // 역할(Role) 이름

    private Long residentId;
    private String residentName;
    private String residentImage;
}
