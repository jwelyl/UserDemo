package com.a504.userdemo.dto;

import com.a504.userdemo.entity.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String email;
    private final String name;

    private final String nickName;
    private final LocalDateTime modifiedDate;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.modifiedDate = user.getModifiedDate();
    }
}
