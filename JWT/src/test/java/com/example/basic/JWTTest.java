package com.example.basic;

import com.example.basic.entity.User;
import com.example.basic.repository.UserRepository;
import com.example.basic.security.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
public class JWTTest {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void save_users() {
        User user1 = User.builder()
                .name("Lai Duy Nghia")
                .email("nghia@gmail.com")
                .password(passwordEncoder.encode("111"))
                .role(List.of("USER", "EDITOR", "ADMIN"))
                .enabled(true)
                .build();

        User user2 = User.builder()
                .name("Nguyen Van A")
                .email("nva@gmail.com")
                .password(passwordEncoder.encode("111"))
                .role(List.of("USER", "EDITOR"))
                .enabled(true)
                .build();

        User user3 = User.builder()
                .name("Nguyen Van B")
                .email("nvb@gmail.com")
                .password(passwordEncoder.encode("111"))
                .role(List.of("USER"))
                .enabled(true)
                .build();

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @Test
    void generate_token_test() {
        User user = userRepository.findByEmail("nghia@gmail.com").get();
        String token = jwtUtils.generateToken(user);

        System.out.println(token);
    }

}
