package com.smartcarebackend.security.request;

import lombok.Data;

@Data
public class ChangePwdRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private Long userId;
}
