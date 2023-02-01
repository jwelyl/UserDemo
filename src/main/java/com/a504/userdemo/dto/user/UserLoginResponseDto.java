package com.a504.userdemo.dto.user;

import com.a504.userdemo.entity.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserLoginResponseDto {
    private final Long userId;
    private final List<String> roles;
    private final LocalDateTime createdDate;

    public UserLoginResponseDto(User user) {
        this.userId = user.getUserId();
        this.roles = user.getRoles();
        this.createdDate = user.getCreatedDate();
    }
}
