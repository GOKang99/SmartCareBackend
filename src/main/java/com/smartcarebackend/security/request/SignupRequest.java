package com.smartcarebackend.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "이름을 입력해야 합니다.")
    @Size(min=2,max=30)
    private String username;

    @NotBlank(message = "올바른 이메일 형식이어야 합니다.")
    @Size(max=50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank(message = "비밀번호는 4자 이상")
    @Size(min=4, max=40)
    private String password;

    private String confirmPassword;
    private String phone;
    private String address;
    private String ssn;
    private String relation;
    private String realname;
    private Long userId;
}
