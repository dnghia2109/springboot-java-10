package vn.techmaster.bookonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.techmaster.bookonline.entity.Author;
import vn.techmaster.bookonline.entity.Book;
import vn.techmaster.bookonline.entity.Category;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    // Find all, pageable order by name
    Page<Book> findByOrderByNameAsc(Pageable pageable);

    // Find by name, pageable order by name
    Page<Book> findByNameContainsIgnoreCaseOrderByNameAsc(String name, Pageable pageable);



    // Find by name
    @Query("select b from Book b where upper(b.name) like upper(concat('%', ?1, '%'))")
    List<Book> findByName(String name);

    // Find by category
    List<Book> findByCategories(Category category);

    // Find by author
    List<Book> findByAuthors(Author author);

    @Query("""
            select b from Book b
            where b.categories in ?1 and b.price between ?2 and ?3 and b.pages between ?4 and ?5 and upper(b.name) like upper(concat('%', ?6, '%'))""")
    List<Book> filterBooks(
            Collection<Category> categories, Long priceStart, Long priceEnd, Integer pagesStart, Integer pagesEnd,
            String name);
}