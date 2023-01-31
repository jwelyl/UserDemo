package com.a504.userdemo.advice;

import com.a504.userdemo.advice.exception.EmailLoginFailedCException;
import com.a504.userdemo.advice.exception.EmailSignupFailedCException;
import com.a504.userdemo.advice.exception.UserNotFoundException;
import com.a504.userdemo.model.response.CommonResult;
import com.a504.userdemo.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;
    private final MessageSource messageSource;

    /**
     * default Exception
     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
//        return responseService.getFailResult();
//    }

    /**
     * default Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        log.info(String.valueOf(e));
        return responseService.getFailResult
                (Integer.parseInt(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    /**
     * 유저 이메일 로그인 실패 시 발생시키는 에외
     */
    @ExceptionHandler(EmailLoginFailedCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedException(HttpServletRequest request, EmailLoginFailedCException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("emailLgoinFailed.code")),
                getMessage("emailLoginFailed.msg"));
    }

    /**
     * 회원 가입 시 이미 로그인된 이메일인 경우 발생시키는 예외
     */
    @ExceptionHandler(EmailSignupFailedCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSignupFailedExdeption(HttpServletRequest request, EmailSignupFailedCException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("emailSignupFailed")), getMessage("emailSignupFailed.msg"));
    }

    /**
     *  유저를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
        return responseService.getFailResult
                (Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
