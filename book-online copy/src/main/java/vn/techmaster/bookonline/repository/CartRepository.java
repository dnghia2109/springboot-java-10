package vn.techmaster.bookonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.bookonline.entity.Cart;
import vn.techmaster.bookonline.entity.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    // Find by user
    Optional<Cart> findByUser(User user);

}