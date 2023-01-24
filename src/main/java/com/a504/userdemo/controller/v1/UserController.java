package com.a504.userdemo.controller.v1;

import com.a504.userdemo.entity.User;
import com.a504.userdemo.repository.UserJpaRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;

    @ApiOperation(value = "모든 회원 조회", notes = "모든 회원 목록을 조회합니다.")
    @GetMapping("/users")
    public List<User> findAllUser() {
        return userJpaRepo.findAll();
    }

    @ApiOperation(value = "회원 등록", notes = "회원을 등록합니다.")
    @PostMapping("/user")
    public User save(@ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .email(email)
                .name(name)
                .build();

        return userJpaRepo.save(user);
    }

    @ApiOperation(value = "회원 검색 (이름)", notes = "이름으로 회원을 검색합니다.")
    @GetMapping("/find-user-by-name/{name}")
    public User findUserByName(@ApiParam(value = "회원 이름", required = true) @PathVariable String name) {
        return userJpaRepo.findByName(name);
    }

    @ApiOperation(value = "회원 검색 (이메일)", notes = "이메일로 회원을 검색합니다.")
    @GetMapping("/find-user-by-email/{email}")
    public User findUserByEmail(@ApiParam(value = "회원 이메일", required = true) @PathVariable String email) {
        return userJpaRepo.findByEmail(email);
    }
}
