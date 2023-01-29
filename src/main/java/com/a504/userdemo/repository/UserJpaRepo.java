package com.a504.userdemo.repository;

import com.a504.userdemo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {
    User findByName(String name);
    User findByEmail(String email);
}
