package com.a504.userdemo.controller.v1;

import com.a504.userdemo.dto.UserRequestDto;
import com.a504.userdemo.dto.UserResponseDto;
import com.a504.userdemo.model.response.CommonResult;
import com.a504.userdemo.model.response.ListResult;
import com.a504.userdemo.model.response.SingleResult;
import com.a504.userdemo.service.ResponseService;
import com.a504.userdemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "서버 테스트", notes = "서버 작동 확인")
    @GetMapping("/hello")
    public SingleResult<String> hello() {
        return responseService.getSingleResult("hello");
    }

    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원을 조회합니다.")
    @GetMapping("/user/{userId}")
    public SingleResult<UserResponseDto> findUserById
            (@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return responseService.getSingleResult(userService.findById(userId));
    }

    @ApiOperation(value = "회원 단건 검색 (이메일)", notes = "이메일로 회원을 조회합니다.")
    @GetMapping("/user/email/{email}")
    public SingleResult<UserResponseDto> findUserByEmail
            (@ApiParam(value = "회원 이메일", required = true) @PathVariable String email,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return responseService.getSingleResult(userService.findByEmail(email));
    }

    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/users")
    public ListResult<UserResponseDto> findAllUser() {
        return responseService.getListResult(userService.findAllUser());
    }

    @ApiOperation(value = "회원 등록", notes = "회원을 등록합니다.")
    @PostMapping("/user")
    public SingleResult<Long> save(@ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                                   @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        UserRequestDto user = UserRequestDto.builder()
                .email(email)
                .name(name)
                .build();
        return responseService.getSingleResult(userService.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정합니다.")
    @PutMapping("/user")
    public SingleResult<Long> modify(@ApiParam(value = "회원 아이디", required = true) @RequestParam Long userId,
                                     @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        UserRequestDto user = UserRequestDto.builder()
                .email(email)
                .name(name)
                .build();
//        User user = userJpaRepo.findById(userId).orElse(null);
//
//        user.setEmail(email);
//        user.setName(name);

        return responseService.getSingleResult(userService.update(userId, user));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제합니다.")
    @DeleteMapping("/user/{userId}")
    public CommonResult delete(@ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        userService.delete(userId);
        return responseService.getSuccessResult();
    }
}
