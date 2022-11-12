package vn.techmaster.bookonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.bookonline.entity.Book;
import vn.techmaster.bookonline.entity.Cart;
import vn.techmaster.bookonline.entity.CartBook;

import java.util.List;
import java.util.Optional;

public interface CartBookRepository extends JpaRepository<CartBook, String> {
    // Find by cart
    List<CartBook> findByCart(Cart cart);

    // Find by cart and book
    Optional<CartBook> findByCartAndBook(Cart cart, Book book);
}