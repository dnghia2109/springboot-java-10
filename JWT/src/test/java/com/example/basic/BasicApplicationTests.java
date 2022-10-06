package com.example.basic;

import com.example.basic.entity.User;
import com.example.basic.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BasicApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void save_user_test() {
        User user1 = User.builder()
                .name("Bùi Hiên")
                .email("hien@gmail.vn")
                .password(passwordEncoder.encode("111"))
                .enabled(true)
                .role(new ArrayList<>(List.of("USER", "ADMIN")))
                .build();

        User user2 = User.builder()
                .name("Thu Hằng")
                .email("hang@gmail.com")
                .password(passwordEncoder.encode("111"))
                .enabled(true)
                .role(new ArrayList<>(List.of("USER")))
                .build();

        userRepository.saveAll(List.of(user1, user2));
    }
}
