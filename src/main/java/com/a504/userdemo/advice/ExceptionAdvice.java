package com.a504.userdemo.advice;

import com.a504.userdemo.advice.exception.UserNotFoundException;
import com.a504.userdemo.model.response.CommonResult;
import com.a504.userdemo.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

    /**
     * default Exception
     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
//        return responseService.getFailResult();
//    }

    /**
     *  유저를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
        return responseService.getFailResult();
    }
}
