package com.a504.userdemo.service;

import com.a504.userdemo.advice.exception.UserNotFoundException;
import com.a504.userdemo.dto.UserRequestDto;
import com.a504.userdemo.dto.UserResponseDto;
import com.a504.userdemo.entity.User;
import com.a504.userdemo.repository.UserJpaRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserJpaRepo userJpaRepo;

    @Transactional
    public Long save(UserRequestDto userDto) {
        userJpaRepo.save(userDto.toEntity());
        return userJpaRepo.findByName(userDto.getEmail()).getUserId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        else return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userJpaRepo.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserRequestDto update(Long id, UserRequestDto userRequestDto) {
        User modifiedUser = userJpaRepo.findById(id).orElseThrow(UserNotFoundException::new);
        return UserRequestDto.builder()
                .name(modifiedUser.getName())
                .email(modifiedUser.getEmail())
                .build();
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }
}
