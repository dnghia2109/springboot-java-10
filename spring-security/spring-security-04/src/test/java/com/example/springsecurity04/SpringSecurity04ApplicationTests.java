package com.example.springsecurity04;

import com.example.springsecurity04.entity.User;
import com.example.springsecurity04.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootTest
class SpringSecurity04ApplicationTests {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_users() {
        User user1 = User.builder()
                .name("Lai Duy Nghia")
                .email("nghia@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of("USER", "EDITOR", "ADMIN"))
                .enabled(true)
                .build();

        User user2 = User.builder()
                .name("Nguyen Van A")
                .email("nva@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of("USER", "EDITOR"))
                .enabled(true)
                .build();

        User user3 = User.builder()
                .name("Nguyen Van B")
                .email("nvb@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of("USER"))
                .enabled(true)
                .build();

        userRepository.saveAll(List.of(user1, user2, user3));
    }

}
