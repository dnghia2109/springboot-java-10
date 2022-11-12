package vn.techmaster.bookonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.entity.Book;
import vn.techmaster.bookonline.entity.Cart;
import vn.techmaster.bookonline.entity.CartBook;
import vn.techmaster.bookonline.entity.User;
import vn.techmaster.bookonline.exception.NotFoundException;
import vn.techmaster.bookonline.repository.CartRepository;

import java.util.List;
import java.util.Set;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart findByUser(User user) {
        return cartRepository.findByUser(user).orElseThrow(() -> new NotFoundException("No cart found"));
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Long getTotalPrice(Cart cart) {
        Long sum = 0L;

        Set<CartBook> cartBooks = cart.getCartBooks();
        for (CartBook cartBook : cartBooks) {
            sum += cartBook.getBook().getPrice() * cartBook.getQuantity();
        }

        return sum;
    }
}
