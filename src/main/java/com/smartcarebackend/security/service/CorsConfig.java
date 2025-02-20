package com.smartcarebackend.security.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration //설정 클래스라고 명시
public class CorsConfig {

    @Bean //CORS설정을 반환하는 메서드
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); //cors 설정 객체 생성
        configuration.setAllowedOriginPatterns(List.of("*")); //모든 패턴의 도메인 허용
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); //허용할 HTTP 메서드 정의
        configuration.setAllowedHeaders(List.of("*")); //모든 HTTP 헤더 허용
        configuration.setAllowCredentials(true); //자격 증명을 포함한 요청을 허용(쿠기, HTTP 인증 정보를 허용)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //URL패턴에 대한 cors 설정 객체
        source.registerCorsConfiguration("/**", configuration); //모든 패턴의 경로에 대해 cors설정을 적용
        return source; //cors설정이 적용된 경로들에 대한 설정 객체
    }
}
