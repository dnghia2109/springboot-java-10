package vn.techmaster.bookonline.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.bookonline.entity.Book;
import vn.techmaster.bookonline.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    // Find by book
    List<Comment> findByBook(Book book, Sort sort);
}