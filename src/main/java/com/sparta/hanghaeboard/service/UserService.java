package com.sparta.hanghaeboard.service;


import com.sparta.hanghaeboard.dto.StatusDto;
import com.sparta.hanghaeboard.dto.UserRequestsDto;
import com.sparta.hanghaeboard.entity.StatusEnum;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.entity.UserRoleEnum;
import com.sparta.hanghaeboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<StatusDto> userSignup(UserRequestsDto userRequestsDto) {
        String username = userRequestsDto.getUsername();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 사용자 ROLE 확인
        if (userRequestsDto.isAdmin()) {
            if (!userRequestsDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            userRequestsDto.setRole(UserRoleEnum.ADMIN);
        }

        User user = new User(userRequestsDto);
        userRepository.save(user);

        StatusDto statusMessageDto = StatusDto.setSuccess(StatusEnum.OK.getStatusCode(), "회원가입 완료", null);
        return new ResponseEntity(statusMessageDto, HttpStatus.OK);
    }
}
