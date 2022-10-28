package com.example.springsecurity;

import com.example.springsecurity.entity.User;
import com.example.springsecurity.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class SpringSecurityApplicationTests {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
//    @Rollback(value = false)
    void save_users() {

        User user1 = User.builder()
                .name("Lai Duy Nghia")
                .email("nghia@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of("USER", "EDITOR", "ADMIN"))
                .build();

        User user2 = User.builder()
                .name("Nguyen Duc A")
                .email("A@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of("USER", "EDITOR"))
                .build();

        User user3 = User.builder()
                .name("Tran Van B")
                .email("B@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of("USER"))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

}
