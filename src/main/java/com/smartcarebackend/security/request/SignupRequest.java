package com.smartcarebackend.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Setter;

import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min=2,max=30)
    private String username;

    @NotBlank
    @Size(max=50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min=4, max=40)
    private String password;
}
