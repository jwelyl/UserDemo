package com.a504.userdemo.controller;

import com.a504.userdemo.advice.exception.CAuthenticationEntryPointException;
import com.a504.userdemo.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {
    @GetMapping("/entryPoint")
    public CommonResult entryPointException() {
        throw new CAuthenticationEntryPointException();
    }
    @GetMapping("/accessDenied")
    public CommonResult accessDeniedException() {
        System.out.println("AccessDeniedException!!!!!!!!!");
        throw new AccessDeniedException("");
    }
}
