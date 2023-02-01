package com.a504.userdemo.controller.v1;

import com.a504.userdemo.dto.jwt.TokenDto;
import com.a504.userdemo.dto.jwt.TokenRequestDto;
import com.a504.userdemo.dto.user.UserLoginRequestDto;
import com.a504.userdemo.dto.user.UserLoginResponseDto;
import com.a504.userdemo.dto.user.UserSignupRequestDto;
import com.a504.userdemo.model.response.SingleResult;
import com.a504.userdemo.service.ResponseService;
import com.a504.userdemo.service.security.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(value = "1. SignUp / LogIn")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/sign")
public class SignController {
    private final SignService signService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인합니다.")
    @PostMapping("/login")
    public SingleResult<TokenDto> login(
            @ApiParam(value = "로그인 요청 DTO", required = true)
            @RequestBody UserLoginRequestDto userLoginRequestDto) {
      TokenDto tokenDto = signService.login(userLoginRequestDto);
      return responseService.getSingleResult(tokenDto);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup(
        @ApiParam(value = "회원 가입 요청 DTO", required = true)
        @RequestBody UserSignupRequestDto userSignupRequestDto) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println("userSignupRequestDto = " + userSignupRequestDto);
        Long signupId = signService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }

    @ApiOperation(
            value = "액세스, 리프레시 토큰 재발급",
            notes = "액세스 토큰 만료시 회원 검증 후 리프레쉬 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급합니다."
    )
    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(
            @ApiParam(value = "토큰 재발급 요청 DTO", required = true)
            @RequestBody TokenRequestDto tokenRequestDto
            ) {
        System.out.println("tokenRequestDto = " + tokenRequestDto);
        return responseService.getSingleResult(signService.reissue(tokenRequestDto));
    }
}
