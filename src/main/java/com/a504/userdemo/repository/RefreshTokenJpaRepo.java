package com.a504.userdemo.repository;

import com.a504.userdemo.entity.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepo extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByKey(Long key);
}
