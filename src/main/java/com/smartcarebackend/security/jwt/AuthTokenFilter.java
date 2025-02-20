package com.smartcarebackend.security.jwt;

import com.smartcarebackend.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());
        try {
            String jwt=parseJwt(request);
            if(jwt !=null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                //유저네임으로 유저엔티티를 찾은 뒤 해당 엔티티를 유저디테일 객체로 만들어서 반환
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //jwt토큰 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                logger.debug("Roles from JWT: {}", userDetails.getAuthorities());
                //사용자의 인증객체에 추가적인 정보(요청한 사용자의 ip주소)를 설정
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //시큐리티 유저인증 업데이트, 현재 저장된 securityContext에 새로운 인증객체를 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        //토큰 인증 필터가 끝난 뒤 기본 인증 필터로 넘겨준다(doFilter)
        //체인필터는 시큐리티에서 설정한 필터들의 집합이며 설정해준 순서대로 필터가 실행된다
        //doFilter를 이용해서 다음 필터가 실행되도록 한다(요청과 응답을 처리한 뒤 다음 필터로 전달)
        filterChain.doFilter(request, response);
    }

    //헤더에서 토큰을 가져와서 jwt객체 반환
    private String parseJwt(HttpServletRequest request){
        String jwt=jwtUtils.getJwtFromHeader(request);
        logger.debug("AuthTokenFilter.java: {}",jwt);
        return jwt;
    }
}
