package vn.techmaster.bookonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.bookonline.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Override
    Optional<Order> findById(String s);


}