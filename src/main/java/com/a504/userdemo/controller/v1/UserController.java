package com.a504.userdemo.controller.v1;

import com.a504.userdemo.entity.User;
import com.a504.userdemo.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;

    @GetMapping("/users")
    public List<User> findAllUser() {
        return userJpaRepo.findAll();
    }

    @PostMapping("/user")
    public User save() {
        User user = User.builder()
                .email("cork2586@naver.com")
                .name("허재성")
                .build();

        return userJpaRepo.save(user);
    }

    @GetMapping("/find-user-by-name/{name}")
    public User findUserByName(@PathVariable String name) {
        return userJpaRepo.findByName(name);
    }

    @GetMapping("/find-user-by-email/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return userJpaRepo.findByEmail(email);
    }
}
