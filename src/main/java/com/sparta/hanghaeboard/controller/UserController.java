package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.StatusDto;
import com.sparta.hanghaeboard.dto.UserRequestsDto;
import com.sparta.hanghaeboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<StatusDto> userSignup(@RequestBody @Valid UserRequestsDto userRequestsDto) {
        return userService.userSignup(userRequestsDto);
    }


}
