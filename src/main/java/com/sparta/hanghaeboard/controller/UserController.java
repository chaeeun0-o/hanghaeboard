package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.LoginRequestsDto;
import com.sparta.hanghaeboard.dto.StatusDto;
import com.sparta.hanghaeboard.dto.UserRequestsDto;
import com.sparta.hanghaeboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<StatusDto> userSignup(@RequestBody @Valid UserRequestsDto userRequestsDto) {
        return userService.userSignup(userRequestsDto);
    }
    // 로그인 :) Url받을때 는 @PathVariable 로 받고 Body 에 아이디랑 비밀번호를 같이 보낼때는 @RequestBody 를 사용합니다.
    // url 에 사용자의 아이디와 비밀번호를 받을 경우 보안에 안좋지 않을까요? 근데 왜 Path 로 받으세요 지금 ㅠㅠ 굿
    // dto 같은 경우는 하나의 기능에 하나의 역할을 할 수 있도록 분리 해주는게 좋습니다. 라고 매니저님이 말씀하셨습니다. 낭비되는 리소스가 없도록. 유지보수도 편하구요.

    // Header 정보를 가져오는경우 -> HttpServletRequest 보내주는경우 Response
    @PostMapping("/api/login")
    public ResponseEntity<StatusDto> login (@RequestBody LoginRequestsDto loginRequestsDto, HttpServletResponse response){
        return userService.userLogin(loginRequestsDto, response);
    }




}
