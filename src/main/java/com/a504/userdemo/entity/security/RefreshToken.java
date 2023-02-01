package com.a504.userdemo.entity.security;

import com.a504.userdemo.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor
public class RefreshToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private Long key;

    @Column(nullable = false)
    private String token;

    public RefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }

    @Builder
    public RefreshToken(Long key, String token) {
        this.key = key;
        this.token = token;
    }
}
