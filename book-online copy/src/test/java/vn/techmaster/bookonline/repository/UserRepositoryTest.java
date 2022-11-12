package vn.techmaster.bookonline.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.techmaster.bookonline.exception.NotFoundException;
import vn.techmaster.bookonline.entity.Gender;
import vn.techmaster.bookonline.entity.Status;
import vn.techmaster.bookonline.entity.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    // Find by id
    @Test
    void findById() {
        User testUser = User.builder()
                .username("test")
                .email("test@gmail.com")
                .mobile("123")
                .password("123")
                .fullName("test")
                .gender(Gender.FEMALE)
                .dob(LocalDate.now().minusYears(20))
                .status(Status.ACTIVE)
                .roles(List.of("customer"))
                .build();
        userRepository.save(testUser);
        String id = testUser.getId();
        User foundedUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Test failed"));
        assertThat(foundedUser.getId()).isEqualTo(id);
    }

    // Find all
    @Test
    void findAll() {
        assertThat(userRepository.findAll()).size().isEqualTo(3);
    }
}
