package vn.techmaster.bookonline;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.techmaster.bookonline.entity.*;
import vn.techmaster.bookonline.repository.*;
import vn.techmaster.bookonline.service.BookService;
import vn.techmaster.bookonline.service.CommentService;
import vn.techmaster.bookonline.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

public class MockDataInitializer {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private Faker faker;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final Random random = new Random();

    @Transactional
    public void initData() {
        // Begin create admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gmail.com");
        admin.setMobile(faker.phoneNumber().cellPhone());
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setFullName("Admin");
        admin.setGender(Gender.FEMALE);
        admin.setDob(LocalDate.now().minusYears(20));
        admin.setAvatar(null);
        admin.setStatus(Status.ACTIVE);
        admin.setRoles(List.of("ADMIN", "USER"));

        userRepository.save(admin);
        // End create admin

        // Begin create active users and carts
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername(faker.name().username() + i); // Plus i to avoid duplication
            user.setEmail("user" + i + "@gmail.com");
            user.setMobile(faker.phoneNumber().cellPhone());
            user.setPassword(passwordEncoder.encode("123"));
            user.setFullName(faker.name().fullName());
            user.setGender(Gender.values()[random.nextInt(Gender.values().length)]);
            user.setDob(LocalDate.now().minusYears(random.nextInt(70 - 18) + 18)
                    .minusDays(random.nextInt(365)));
            user.setHomeAddress(random.nextInt(2) == 0 ? null : faker.address().fullAddress());
            user.setWorkAddress(random.nextInt(2) == 0 ? null : faker.address().fullAddress());
            user.setAvatar("upload/user-avatars/user" + i + ".jpg");
            user.setStatus(Status.ACTIVE);
            user.setRoles(List.of("USER"));

            users.add(user);

            userRepository.save(user);

            Cart cart = new Cart();
            cart.setUser(userService.findById(user.getId()));

            cartRepository.save(cart);
        }
        // End create active users and cart

        // Begin create authors
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Author author = Author.builder()
                    .fullName(faker.name().fullName())
                    .gender(Gender.values()[random.nextInt(Gender.values().length)])
                    .address(faker.address().fullAddress())
                    .yearOfBirth(random.nextInt(2000 - 1700) + 1700)
                    .books(null)
                    .build();
            if (author.getYearOfBirth() < 1950) {
                author.setYearOfDeath(author.getYearOfBirth() + random.nextInt(100 - 20) + 20);
            }
            authors.add(author);
        }
        authorRepository.saveAll(authors);
        // End create authors

        // Begin create categories
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category category = Category.builder()
                    .name(faker.book().genre() + " " + i) // Plus i to avoid duplication
                    .build();
            categories.add(category);
        }
        categoryRepository.saveAll(categories);
        // End create categories

        // Begin create publishers
        List<Publisher> publishers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Publisher publisher = Publisher.builder()
                    .name(faker.book().publisher() + " " + i) // Plus i to avoid duplication
                    .build();
            publishers.add(publisher);
        }
        publisherRepository.saveAll(publishers);
        // End create publishers

        // Begin create books
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Book book = new Book();
            book.setName(faker.book().title());
            book.setPublisher(publishers.get(random.nextInt(publishers.size())));
            book.setPublishedYear(random.nextInt(2000 - 1700) + 1700);
            book.setPages(random.nextInt(1000) + 50);
            book.setQuantity(random.nextInt(1000));
            book.setThumbnail("upload/book-thumbnails/book" + i + ".jpg");
            book.setDescription(faker.lorem().paragraph(10));
            book.setPrice((random.nextLong(500L - 10) + 10) * 1000);

            Set<Author> randomAuthors = new LinkedHashSet<>();
            randomAuthors.add(authors.get(random.nextInt(authors.size())));
            book.setAuthors(randomAuthors);

            for (int j = 0; j < 3; j++) {
                Category randomCategory = categories.get(random.nextInt(categories.size()));
                bookService.addCategory(book, randomCategory);
            }

            books.add(book);
        }
        bookRepository.saveAllAndFlush(books);
        // End create books

        // Begin create comments
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Comment comment = new Comment();
            comment.setContent(faker.lorem().paragraph(random.nextInt(5) + 1));

            User randomUser = users.get(random.nextInt(users.size()));
            Book randomBook = books.get(random.nextInt(books.size()));
            commentService.addUserAndBook(comment, randomUser, randomBook);

            comments.add(comment);
        }
        // No need to save comments because they are saved due to cascade of user and book
        // End create comments
    }
}
