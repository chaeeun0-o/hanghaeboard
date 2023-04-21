package com.sparta.hanghaeboard.service;


import com.sparta.hanghaeboard.dto.LoginRequestsDto;
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

    // 로그인
    public String userLogin(LoginRequestsDto loginRequestsDto) {

        User user = userRepository.findByUsername(loginRequestsDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        //DB
        // ID : Sparta
        // PW : 123123

        // loginRequestDto
        // ID : Sparta
        // PW : 111111

        // User user == db
        // ID : Sparta
        // PW : 123123

        // findByUsername 은 username GetUsername 이랑 다릅니다.
        // findByUsername 은 username 을 찾아서 그랑 일치하는 테이블(아이디에 일치하는 하위 데이터들 모두) 을 가져다 새로운 객체를 생성해주는 겁니다.

        if (!user.getPassword().equals(loginRequestsDto.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        // JWT + 상태코드
        return "로그인 성공";
    }
}
