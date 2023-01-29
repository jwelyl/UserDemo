package com.a504.userdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UserDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDemoApplication.class, args);
    }

}
