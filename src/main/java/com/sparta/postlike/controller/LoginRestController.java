package com.sparta.postlike.controller;

import com.sparta.postlike.domain.SignUpRequestDto;
import com.sparta.postlike.domain.Users;
import com.sparta.postlike.domain.UsersRepository;
import com.sparta.postlike.springsecurityjwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LoginRestController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/api/signup", consumes = {"application/json"})
    public String signUp(@RequestBody SignUpRequestDto requestDto){
        Users users = new Users(requestDto);
        usersRepository.save(users.builder()
                .email(users.getEmail())
                .password(passwordEncoder.encode(users.getPassword()))
                .nickname(requestDto.getNickname())
                .roles(Collections.singletonList("ROLE_USER"))
                .build()).getId();
        return "";
    }
    @PostMapping("/api/login")
    public String login(@RequestBody Map<String, String> user){
        Users member = usersRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if(!passwordEncoder.matches(user.get("password"), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getEmail(), member.getNickname(), member.getId(), member.getRoles());
    }

    @GetMapping("/api/logout")
    public String logout(){ 
        // 로그아웃의 경우 redis 를 사용하서 토큰 제거가 가능하다고 하여
        // 시간이 남는 경우 추가 예정
        return "로그아웃 성공";
    }

//    @GetMapping("/api/token")
//    public String tokenCheck(@RequestHeader Map<String, String> token) {
//        System.out.println("이거야" + token.get("authentication"));
//        System.out.println(jwtTokenProvider.getUserPk(token.get("authentication")));
//        return "yes";
//    }

}
