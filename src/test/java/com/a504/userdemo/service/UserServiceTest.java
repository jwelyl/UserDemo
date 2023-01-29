package com.a504.userdemo.service;

import com.a504.userdemo.advice.exception.UserNotFoundException;
import com.a504.userdemo.dto.UserRequestDto;
import com.a504.userdemo.dto.UserResponseDto;
import com.a504.userdemo.entity.user.User;
import com.a504.userdemo.repository.UserJpaRepo;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    UserService userService;

    @Autowired
    UserJpaRepo userJpaRepo;

    @Test
    @DisplayName("등록 테스트")
    public void 등록() {
        //  given
        UserRequestDto userA = UserRequestDto.builder()
                .name("허재성")
                .email("cork2586@naver.com")
                .build();
        Long saveId = userService.save(userA);

        //  when
        UserResponseDto userB = userService.findById(saveId);

        //  then
        Assertions.assertThat(userB.getName()).isEqualTo(userA.getName());
        Assertions.assertThat(userB.getEmail()).isEqualTo(userA.getEmail());
    }

    @Test
    @DisplayName("저장 후 이름, 이메일 확인")
    public void 저장후_이메일_이름비교() {
        //  given
        UserRequestDto userA = UserRequestDto.builder()
                .name("허재성")
                .email("cork2586@naver.com")
                .build();
        Long id = userService.save(userA);

        //  when
        UserResponseDto dtoA = userService.findById(id);

        //  then
        Assertions.assertThat(userA.getName()).isEqualTo(dtoA.getName());
        Assertions.assertThat(userA.getEmail()).isEqualTo(dtoA.getEmail());
    }

    @Test
    @DisplayName("모든 회원 조회")
    public void 모든_회원_조회() {
        //  given
        UserRequestDto userA = UserRequestDto.builder()
                .name("허재성")
                .email("cork2586@naver.com")
                .build();
        userService.save(userA);
        UserRequestDto userB = UserRequestDto.builder()
                .name("jwelyl")
                .email("koreii@naver.com")
                .build();
        userService.save(userB);

        //  when
        List<UserResponseDto> allUsers = userService.findAllUser();

        //  then
        Assertions.assertThat(allUsers.size()).isSameAs(2);
    }

    @Test
    @DisplayName("회원 이름 수정")
    public void 수정() {
        //  given
        UserRequestDto userA = UserRequestDto.builder()
                .name("허재성")
                .email("cork2586@naver.com")
                .build();
        Long id = userService.save(userA);

        UserRequestDto userB = UserRequestDto.builder()
                .name("허줴릴")
                .email("cork2586@naver.com")
                .build();
        //  when
        userService.update(id, userB);

        //  then
        Assertions.assertThat(userService.findById(id).getName()).isEqualTo("허줴릴");
    }

    @Test
    @DisplayName("회원 삭제")
    public void 삭제() {
        //  given
        UserRequestDto userA = UserRequestDto.builder()
                .name("허재성")
                .email("cork2586@naver.com")
                .build();
        Long saveId = userService.save(userA);

        //  when
        userService.delete(saveId);

        //  then
        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> userService.findById(saveId));
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        //  given
        LocalDateTime now = LocalDateTime.of(2023, 1, 29, 17, 4, 30);
        userJpaRepo.save(User.builder()
                .name("허재성")
                .email("cork2586@naver.com")
                .build());

        //  when
        List<User> users = userJpaRepo.findAll();

        //  then
        User user = users.get(0);

        System.out.println(">>>>>>>>>>>> createDate = " + user.getCreatedDate() + ", modifiedDate = " + user.getModifiedDate());

        Assertions.assertThat(user.getCreatedDate()).isAfter(now);
        Assertions.assertThat(user.getModifiedDate()).isAfter(now);



    }
}
