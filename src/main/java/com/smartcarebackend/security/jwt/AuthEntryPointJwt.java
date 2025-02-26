package com.smartcarebackend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
//토큰 인증에 실패했을 경우 어떻게 응답할것인지 정의하는 클래스
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        System.out.println(authException);

        //응답의 컨텐츠 타입을 json으로
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //인증되지 않았거나 인증 실패시 http 응답 상태를 401 에러로 반환
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        //401에러 헤더의 body 정의
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("message", "Unauthorized");
        body.put("message",authException.getMessage());
        body.put("path",request.getServletPath());

        // AuthenticationException의 원인으로 JWT 관련 예외를 검사
        Throwable cause = authException.getCause();
        if (cause instanceof ExpiredJwtException) {
            // ExpiredJwtException의 기본 메시지를 사용
            body.put("message", cause.getMessage());
        } else if (cause instanceof MalformedJwtException) {
            // MalformedJwtException의 기본 메시지를 사용
            body.put("message", cause.getMessage());
        } else if (cause instanceof UnsupportedJwtException) {
            // UnsupportedJwtException의 기본 메시지를 사용
            body.put("message", cause.getMessage());
        } else {
            // 기타 예외에 대해서는 기본 메시지 사용
            body.put("message", authException.getMessage());
        }

        final ObjectMapper mapper = new ObjectMapper();
        //body객체를 JSON으로 변환하여 응답 본문에 포함시킨 뒤 클라이언트로 보냄
        mapper.writeValue(response.getOutputStream(),body);

    }
}
