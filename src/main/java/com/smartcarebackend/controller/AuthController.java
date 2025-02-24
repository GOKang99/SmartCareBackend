package com.smartcarebackend.controller;

import com.smartcarebackend.model.*;
import com.smartcarebackend.repositories.GiverRepository;
import com.smartcarebackend.repositories.GuardRepository;
import com.smartcarebackend.repositories.RoleRepository;
import com.smartcarebackend.repositories.UserRepository;
import com.smartcarebackend.security.jwt.JwtUtils;
import com.smartcarebackend.security.request.LoginRequest;
import com.smartcarebackend.security.request.SignupRequest;
import com.smartcarebackend.security.response.LoginResponse;
import com.smartcarebackend.security.response.MessageResponse;
import com.smartcarebackend.security.response.UserInfoResponse;
import com.smartcarebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encodeer;

    @Autowired
    UserService userService;
    @Autowired
    GiverRepository giverRepository;
    @Autowired
    GuardRepository guardRepository;


    //로그인(시큐리티에서는 jwt 토큰을 발행하지 않으므로 로그인 컨트롤러를 구현해준다)
    @PostMapping("public/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        //클라이언트가 보낸 json형태의 로그인 요청 본문을 LoginRequest 객체로 매핑
        //빈 인증객체 생성
        Authentication authentication;

        try{
            //유저가 입력한 username과 password가 일치하면 토큰을 발행, 아니면 예외처리
            authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.getUsername(),
                                    loginRequest.getPassword()
                            )
                    );
        }catch(AuthenticationException exception){
            Map<String, Object> map=new HashMap<>();
            //잘못된 인증
            map.put("message","Bad credentials");
            //인증이 실패했다고 명시
            map.put("status",false);
            //404에러로 설정하고 map을 응답 본문에 담아서 반환
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        //시큐리티 인증되면 발행된 토큰이 포함된 authentication객체를 시큐리티 컨텍스트에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //인증된 유저디테일을 가져온다(principal은 객체에서 사용자의 주체를 의미)
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();

        //인증된 유저에 jwt토큰 생성
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        //유저의 권한 리스트 가져오기
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item->item.getAuthority())
                .collect(Collectors.toList());

        //유저이름 유저권한 jwt 토큰으로 새로운 로그인 응답 객체 생성
        LoginResponse response=new LoginResponse(
                userDetails.getUsername(),
                roles,
                jwtToken
        );

        //200 ok 상태코드와 함께 response body에 JWT 토큰을 포함해서 response 객체로 리턴
        return ResponseEntity.ok(response);
    }

    //가입 signup
    @PostMapping("/public/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest){
        System.out.println("싸인"+signUpRequest);
        //입력한 username이 이미 존재하는가
        if(userRepository.existsByUsername(signUpRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("이미 존재하는 계정입니다"));
        }
        //입력한 email이 이미 존재하는가
        if(userRepository.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("이미 존재하는 이메일입니다"));
        }

        //새로운 계정 생성
        User user=new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encodeer.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setSsn(signUpRequest.getSsn());
        user.setPhone(signUpRequest.getPhone());

        //signUpRequest(DTO)에서 Role 가져오기
        Set<String> strRoles = signUpRequest.getRole();
        //빈 Role객체 생성
        Role role;


        //DTO에서 가져온 Role이 비어있을 때
        if(strRoles == null || strRoles.isEmpty()){
            role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(()->new RuntimeException("권한을 찾을 수 없습니다"));
        }
        //Role객체에 내용이 있을 때
        else{
            //roleStr의 값을 iterator와 next 조합으로 읽어오기
            String roleStr = strRoles.iterator().next();
            
            //roleStr이 admin일 경우
            if(roleStr.equals("admin")){
                role=roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                        .orElseThrow(()->new RuntimeException("권한을 찾을 수 없습니다"));
            }
            //roleStr이 user일 경우
            else{
                role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                        .orElseThrow(()->new RuntimeException("권한을 찾을 수 없습니다"));
            }
        }
        //유저객체에 권한 저장
        user.setRole(role);
        //DB에 유저엔티티 저장 및 저장된 유저엔티티 반환
        User addUser=userRepository.save(user);
        //첫번째 권한 추출
        String saveRole=strRoles.iterator().next();
        if(saveRole.equals("admin")){
            Giver giver=new Giver();
            giver.setUser(addUser);
            giverRepository.save(giver);
        }else{
            Guard guard=new Guard();
            guard.setUser(addUser);
            guard.setRelation(signUpRequest.getRelation());
            guardRepository.save(guard);
        }
        //200(성공)상태와 메시지를 응답
        return ResponseEntity.ok(new MessageResponse("회원가입이 완료되었습니다"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        //유저디테일의 username으로 User엔티티 찾아오기
        User user = userService.findByUsername(userDetails.getUsername());

        //유저디테일의 권한을 배열로 정의
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item->item.getAuthority())
                .collect(Collectors.toList());

        //UserInfoResponse DTO의 생성자에 맞게 생성
        UserInfoResponse response=new UserInfoResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                roles
        );
        //ok상태로 응답
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username")
    public String getUsername(Principal principal){
        //Principal은 현재 인증된 사용자에 대한 정보가 담겨있다
        return principal.getName() != null ? principal.getName() : "";
    }
}
