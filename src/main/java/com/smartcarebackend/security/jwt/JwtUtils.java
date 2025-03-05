package com.smartcarebackend.security.jwt;


import com.smartcarebackend.model.Giver;
import com.smartcarebackend.model.Guard;
import com.smartcarebackend.model.User;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.GuardRepository;
import com.smartcarebackend.repositories.UserRepository;
import com.smartcarebackend.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class); //로그 출력객체
    private final GiverRepository giverRepository;
    private final GuardRepository guardRepository;
    private final UserRepository userRepository;

    //jwt 비밀키(설정에 있음)
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    //jwt 유효기간(설정에 있음)
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public JwtUtils(GiverRepository giverRepository, GuardRepository guardRepository, UserRepository userRepository) {
        this.giverRepository = giverRepository;
        this.guardRepository = guardRepository;
        this.userRepository = userRepository;
    }

    //jwt 토큰을 헤더에서 가져온다
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("리퀘스트: " + request);
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); //헤더에서 "Bearer(띄어쓰기)"제거, 7번째부터 시작
        }
        return null;
    }

    //토큰에서 유저네임을 가져온다, 토큰은 헤더, 페이로드, 서명으로 구성된  String 타입의 객체이다
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject(); //JWT는 헤더, 페이로드, 서명으로 구성되어 있고 페이로드에서 서브젝트 클레임을 가져온다
    }

    //jwt토큰 생성
    public String generateTokenFromUsername(UserDetails userDetails) {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails; //유저서비스를 임플 클래스로 생성

        String username = userDetailsImpl.getUsername(); //유저네임 가져오기
        String role = userDetailsImpl.getAuthorities().stream()
                .map(roles -> roles.getAuthority().replace("ROLE_", ""))
                .findFirst().orElse(""); //권한리스트에서 첫번째 권한 가져오기
        Long userId = userDetailsImpl.getId(); //유저id 가져오기
        //유저id로 guard엔티티 가져오기

        if (role.equals("ADMIN")) {
            User findUser = userRepository.findById(userId).get();
            Giver findGiver=findUser.getGiver();
            Long findGiverId=findGiver.getGiverId();
            return Jwts.builder()
                    .subject(username)
                    .claim("userId", userId)
                    .claim("partId", findGiverId)
                    .claim("role", role)
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key())
                    .compact();
        }
        else{
            User findUser = userRepository.findById(userId).get();
            Guard findGuard = findUser.getGuard();
            Long findGuardId=findGuard.getGuardId();
            return Jwts.builder()
                    .subject(username)
                    .claim("userId", userId)
                    .claim("partId", findGuardId)
                    .claim("role", role)
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key())
                    .compact();
        }
//        return Jwts.builder()
//                .subject(username)
//                .claim("userId", userId)
//                .claim("role", role)
//                .issuedAt(new Date())
//                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(key())
//                .compact(); 주석 수정11
    }

    //비밀키 값
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //jwt 토큰이 유효한지, 토큰의 페이로드가 변조되지 않았는지 검사, 토큰이 위,변조 되었다면 예외처리 false
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
