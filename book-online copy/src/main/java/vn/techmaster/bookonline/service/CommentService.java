package vn.techmaster.bookonline.service;

import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.dto.CommentRequest;
import vn.techmaster.bookonline.entity.Book;
import vn.techmaster.bookonline.entity.Comment;
import vn.techmaster.bookonline.entity.User;
import vn.techmaster.bookonline.exception.NotFoundException;
import vn.techmaster.bookonline.repository.BookRepository;
import vn.techmaster.bookonline.repository.CommentRepository;
import vn.techmaster.bookonline.repository.UserRepository;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    // Find by id
    public Comment findById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("No comment with id = " + id));
    }

    // Find all
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    // Save entity
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    // Add instance by request

    // Update instance by request

    // Delete by id
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    // Add user and book
    public void addUserAndBook(Comment comment, User user, Book book) {
        comment.setUser(user);
        user.getComments().add(comment);
        comment.setBook(book);
        book.getComments().add(comment);

        commentRepository.saveAndFlush(comment);
        userRepository.saveAndFlush(user);
        bookRepository.saveAndFlush(book);
    }

    // Find by book
    public List<Comment> findByBook(Book book) {
        return commentRepository.findByBook(book, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    // Save by request
    public void saveByRequest(CommentRequest commentRequest, User user, Book book) {
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        addUserAndBook(comment, user, book);
    }
}
