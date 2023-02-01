package com.a504.userdemo.domain.user;

import com.a504.userdemo.advice.exception.CUserNotFoundException;
import com.a504.userdemo.entity.user.User;
import com.a504.userdemo.repository.UserJpaRepo;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserJpaRepoTest {
    @Autowired
    private UserJpaRepo userJpaRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String name = "jaeseong";
    private String email = "cork2586@naver.com";
    private String password = "myPassword";

    @Test
    public void 회원저장_후_이메일로_회원검색() throws Exception {
        //  given
        userJpaRepo.save(User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickName(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        //  when
        User user = userJpaRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        System.out.println("user.getUsername() = " + user.getUsername());

        //  then
        assertNotNull(user);
        assertEquals(user.getUsername(), name);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getNickName()).isEqualTo(name);
    }

}
