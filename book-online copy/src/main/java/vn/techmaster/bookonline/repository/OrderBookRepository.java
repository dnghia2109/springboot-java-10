package vn.techmaster.bookonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.bookonline.entity.OrderBook;

import java.util.Optional;

public interface OrderBookRepository extends JpaRepository<OrderBook, String> {
    @Override
    Optional<OrderBook> findById(String s);
}