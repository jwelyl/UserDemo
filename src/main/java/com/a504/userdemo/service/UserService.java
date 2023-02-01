package com.a504.userdemo.service;

import com.a504.userdemo.advice.exception.CEmailLoginFailedCException;
import com.a504.userdemo.advice.exception.CEmailSignupFailedCException;
import com.a504.userdemo.advice.exception.CUserNotFoundException;
import com.a504.userdemo.dto.user.UserLoginResponseDto;
import com.a504.userdemo.dto.user.UserRequestDto;
import com.a504.userdemo.dto.user.UserResponseDto;
import com.a504.userdemo.dto.user.UserSignupRequestDto;
import com.a504.userdemo.entity.user.User;
import com.a504.userdemo.repository.UserJpaRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private UserJpaRepo userJpaRepo;

    @Transactional
    public Long save(UserRequestDto userDto) {
        User saved = userJpaRepo.save(userDto.toEntity());
        return saved.getUserId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userJpaRepo.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, UserRequestDto userRequestDto) {
        User modifiedUser = userJpaRepo.findById(id).orElseThrow(CUserNotFoundException::new);
        modifiedUser.setName(userRequestDto.getName());
        modifiedUser.setNickName(userRequestDto.getNickName());
        modifiedUser.setEmail(userRequestDto.getEmail());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }
}
