package com.sparta.hanghaeboard.dto;

import com.sparta.hanghaeboard.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestsDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false; // USER
    private String adminToken = ""; //
    private UserRoleEnum role = UserRoleEnum.USER;
}
