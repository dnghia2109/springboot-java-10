package vn.techmaster.bookonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.dto.CartBookRequest;
import vn.techmaster.bookonline.entity.Book;
import vn.techmaster.bookonline.entity.Cart;
import vn.techmaster.bookonline.entity.CartBook;
import vn.techmaster.bookonline.exception.NotFoundException;
import vn.techmaster.bookonline.repository.BookRepository;
import vn.techmaster.bookonline.repository.CartBookRepository;
import vn.techmaster.bookonline.repository.CartRepository;

import java.util.List;

@Service
public class CartBookService {
    @Autowired
    private CartBookRepository cartBookRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    BookRepository bookRepository;

    public CartBook findById(String id) {
        return cartBookRepository.findById(id).orElseThrow(() -> new NotFoundException("No cartBook with id = " + id));
    }

    public List<CartBook> findByCart(Cart cart) {
        return cartBookRepository.findByCart(cart);
    }

    public void saveByRequest(CartBookRequest cartBookRequest, Cart cart, Book book) {
        CartBook cartBook;
        if (cartBookRequest.getId() == null) {
            cartBook = new CartBook();
            cartBook.setCart(cart);
            cartBook.setBook(book);
        } else {
            cartBook = findById(cartBookRequest.getId());
        }

        cartBook.setQuantity(cartBookRequest.getQuantity());

        cartBookRepository.save(cartBook);
    }

    public void delete(CartBook cartBook) {
        cartBook.getCart().getCartBooks().remove(cartBook);
        cartRepository.save(cartBook.getCart());
        cartBook.getBook().getCartBooks().remove(cartBook);
        bookRepository.save(cartBook.getBook());
        cartBookRepository.delete(cartBook);
    }
}
