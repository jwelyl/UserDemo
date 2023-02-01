package com.a504.userdemo.service;

import com.a504.userdemo.advice.exception.CUserNotFoundException;
import com.a504.userdemo.dto.user.UserRequestDto;
import com.a504.userdemo.dto.user.UserResponseDto;
import com.a504.userdemo.dto.user.UserSignupRequestDto;
import com.a504.userdemo.entity.user.User;
import com.a504.userdemo.repository.UserJpaRepo;
import com.a504.userdemo.service.security.SignService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserJpaRepo userJpaRepo;

    @Autowired
    SignService signService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private UserSignupRequestDto getUserSignupRequestDto(int number) {
        return UserSignupRequestDto.builder()
                .name("name" + number).password("password" + number).email("email" + number).nickName("nickName" + number).build();
    }
    @Test
    @DisplayName("등록 테스트")
    public void 등록() {
        //  given
        UserSignupRequestDto userA = getUserSignupRequestDto(1);
        User saveUser = userJpaRepo.save(userA.toEntity(passwordEncoder));

        //  when
        UserResponseDto userB = userService.findById(saveUser.getUserId());
        User byId = userJpaRepo.findById(saveUser.getUserId()).orElseThrow(CUserNotFoundException::new);

        //  then
        assertThat(userA.getName()).isEqualTo(userB.getName());
        assertThat(userA.getEmail()).isEqualTo(userB.getEmail());
        assertThat(userService.findById(saveUser.getUserId()).getEmail()).isEqualTo(byId.getEmail());
    }

    @Test
    @DisplayName("저장 후 이름, 이메일 확인")
    public void 저장후_이메일_이름비교() {
        //  given
        UserSignupRequestDto userA = getUserSignupRequestDto(1);
        User user = userJpaRepo.save(userA.toEntity(passwordEncoder));

        //  when
        UserResponseDto dtoA = userService.findById(user.getUserId());

        //  then
        assertThat(userA.getName()).isEqualTo(dtoA.getName());
        assertThat(userA.getEmail()).isEqualTo(dtoA.getEmail());
    }

    @Test
    @DisplayName("모든 회원 조회")
    public void 모든_회원_조회() {
        //  given
        UserSignupRequestDto userA = getUserSignupRequestDto(1);
        UserSignupRequestDto userB = getUserSignupRequestDto(2);

        //  when
        userJpaRepo.save(userA.toEntity(passwordEncoder));
        userJpaRepo.save(userB.toEntity(passwordEncoder));

        //  then
        List<UserResponseDto> allUser = userService.findAllUser();
        assertThat(allUser.size()).isSameAs(2);
    }

    @Test
    @DisplayName("회원 수정")
    public void 수정() {
        //  given
        UserSignupRequestDto userA = getUserSignupRequestDto(1);
        User user = userJpaRepo.save(userA.toEntity(passwordEncoder));

        //  when
        UserRequestDto updateUser = UserRequestDto.builder().nickName("bbb").build();
        userService.update(user.getUserId(), updateUser);

        //  then
        assertThat(userService);
    }

    @Test
    @DisplayName("회원 삭제")
    public void 삭제() {
        //  given
        UserSignupRequestDto userA = getUserSignupRequestDto(1);
        User user = userJpaRepo.save(userA.toEntity(passwordEncoder));

        //  when
        userService.delete(user.getUserId());

        //  then
        assertThrows(CUserNotFoundException.class, () -> userService.findById(user.getUserId()));
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        //  given
        LocalDateTime now = LocalDateTime.of(2023, 2, 01, 15, 54, 30);
        UserSignupRequestDto userA = getUserSignupRequestDto(1);

        //  when
        userJpaRepo.save(userA.toEntity(passwordEncoder));
        List<User> users = userJpaRepo.findAll();

        //  then
        User firstUser = users.get(0);
        assertThat(firstUser.getCreatedDate()).isAfter(now);
        assertThat(firstUser.getModifiedDate()).isAfter(now);
    }
}
