package com.a504.userdemo.dto;

import com.a504.userdemo.entity.user.Role;
import com.a504.userdemo.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    private String email;
    private String name;
    private String nickName;

    @Builder
    public UserRequestDto(String email, String name, String nickName) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .nickName(nickName)
                .build();
    }

}
