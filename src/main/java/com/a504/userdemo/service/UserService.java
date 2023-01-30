package com.a504.userdemo.service;

import com.a504.userdemo.advice.exception.EmailLoginFailedException;
import com.a504.userdemo.advice.exception.EmailSignupFailedException;
import com.a504.userdemo.advice.exception.UserNotFoundException;
import com.a504.userdemo.dto.UserLoginResponseDto;
import com.a504.userdemo.dto.UserRequestDto;
import com.a504.userdemo.dto.UserResponseDto;
import com.a504.userdemo.dto.UserSignupRequestDto;
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
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserRequestDto userDto) {
        User saved = userJpaRepo.save(userDto.toEntity());
        return saved.getUserId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
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
        User modifiedUser = userJpaRepo.findById(id).orElseThrow(UserNotFoundException::new);
        modifiedUser.setName(userRequestDto.getName());
        modifiedUser.setNickName(userRequestDto.getNickName());
        modifiedUser.setEmail(userRequestDto.getEmail());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(String email, String password) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(EmailLoginFailedException::new);

        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new EmailLoginFailedException();
        return new UserLoginResponseDto(user);
    }

    @Transactional
    public Long signup(UserSignupRequestDto userSignupDto) {
        User user = userJpaRepo.findByEmail(userSignupDto.getEmail()).orElse(null);
        if(user == null)
            return userJpaRepo.save(userSignupDto.toEntity()).getUserId();
        else
            throw new EmailSignupFailedException();
    }
}
