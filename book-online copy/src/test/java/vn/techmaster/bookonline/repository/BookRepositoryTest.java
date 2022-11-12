package vn.techmaster.bookonline.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.techmaster.bookonline.entity.Author;
import vn.techmaster.bookonline.entity.Book;
import vn.techmaster.bookonline.entity.Category;
import vn.techmaster.bookonline.entity.Gender;

import javax.transaction.Transactional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByCategoriesIn() {
        Author author1 = Author.builder()
                .fullName("Author 1")
                .gender(Gender.FEMALE)
                .address("Address author 1")
                .yearOfBirth(1990)
                .build();
        authorRepository.save(author1);

        Category category1 = Category.builder()
                .name("Category 1")
                .build();
        categoryRepository.save(category1);

        Category category2 = Category.builder()
                .name("Category 2")
                .build();
        categoryRepository.save(category2);

        Category category3 = Category.builder()
                .name("Category 3")
                .build();
        categoryRepository.save(category3);

        Category category4 = Category.builder()
                .name("Category 4")
                .build();
        categoryRepository.save(category4);

        Book book1 = Book.builder()
                .name("Book 1")
                .publishedYear(2010)
                .pages(100)
                .quantity(100)
                .thumbnail("test.jpg")
                .description("Description book 1")
                .price(100000L)
                .authors(Set.of(author1))
                .categories(
                        Set.of(category1,
                                category2,
                                category3))
                .build();
        bookRepository.save(book1);

        assertThat(bookRepository.findByCategories(category1)).contains(book1);
        assertThat(bookRepository.findByCategories(category2)).contains(book1);
        assertThat(bookRepository.findByCategories(category3)).contains(book1);
        assertThat(bookRepository.findByCategories(category4)).isEmpty();
    }

}
