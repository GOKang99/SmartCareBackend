package com.smartcarebackend.security.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String jwtToken;
    private String username;
    private List<String> roles;

    //생성자(유저이름, 권한, 토큰)
    public LoginResponse(String username, List<String> roles, String jwtToken){
        this.username=username;
        this.roles=roles;
        this.jwtToken=jwtToken;
    }
}
