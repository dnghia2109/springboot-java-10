package vn.techmaster.bookonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.dto.BookRequest;
import vn.techmaster.bookonline.entity.Author;
import vn.techmaster.bookonline.entity.Book;
import vn.techmaster.bookonline.entity.Category;
import vn.techmaster.bookonline.entity.Comment;
import vn.techmaster.bookonline.exception.NotFoundException;
import vn.techmaster.bookonline.repository.AuthorRepository;
import vn.techmaster.bookonline.repository.BookRepository;
import vn.techmaster.bookonline.repository.CategoryRepository;
import vn.techmaster.bookonline.repository.PublisherRepository;

import java.util.*;

@Service
public class BookService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private FileService fileService;

    // Find by id
    public Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("No book with id = " + id));
    }

    // Find all
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // Save entity
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // Map request from entity
    public BookRequest mapRequestEntity(Book book) {
        BookRequest bookRequest = new BookRequest();

        bookRequest.setId(book.getId());
        bookRequest.setName(book.getName());
        bookRequest.setPublishedYear(book.getPublishedYear());
        bookRequest.setPages(book.getPages());
        bookRequest.setQuantity(book.getQuantity());
        bookRequest.setThumbnail(book.getThumbnail());
        bookRequest.setDescription(book.getDescription());
        bookRequest.setPrice(book.getPrice());
        bookRequest.setCategories(book.getCategories());
        bookRequest.setAuthors(book.getAuthors());
        bookRequest.setPublisher(book.getPublisher());

        return bookRequest;
    }

    // Save entity by request
    public Book saveByRequest(BookRequest bookRequest) {
        Book book;
        if (bookRequest.getId() == null) {
            book = new Book();
        } else {
            book = findById(bookRequest.getId());
        }

        book.setName(bookRequest.getName());
        book.setPublishedYear(bookRequest.getPublishedYear());
        book.setPages(bookRequest.getPages());
        book.setQuantity(bookRequest.getQuantity());
        if (!bookRequest.getMultipartFile().isEmpty()) {
            book.setThumbnail(
                    fileService.uploadBookThumbnail(book.getId(), bookRequest.getMultipartFile()));
        }
        book.setDescription(bookRequest.getDescription());
        book.setPrice(bookRequest.getPrice());
        book.setAuthors(bookRequest.getAuthors());
        book.setCategories(bookRequest.getCategories());
        book.setPublisher(bookRequest.getPublisher());

        return bookRepository.save(book);
    }

    // Delete by id
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    // Get categories names
    public List<String> getCategoriesNames(Book book) {
        return book.getCategories().stream().map(Category::getName).toList();
    }

    // Get authors names
    public List<String> getAuthorsNames(Book book) {
        return book.getAuthors().stream().map(Author::getFullName).toList();
    }

    // Add comment
    public void addComment(Book book, Comment comment) {
        book.addComment(comment);
        bookRepository.save(book);
    }

    // Remove comment
    public void removeComment(Book book, Comment comment) {
        book.removeComment(comment);
        bookRepository.save(book);
    }

    // Add category
    public void addCategory(Book book, Category category) {
        book.addCategory(category);
        bookRepository.save(book);
    }

    // Remove category
    public void removeCategory(Book book, Category category) {
        book.removeCategory(category);
        bookRepository.save(book);
    }

    // Filter books
    public List<Book> filterBooks(Collection<Category> categories, Long priceStart, Long priceEnd, Integer pagesStart, Integer pagesEnd,
                                  String name) {
        return bookRepository.filterBooks(categories, priceStart, priceEnd, pagesStart, pagesEnd, name);
    }

    // Find by category
    public List<Book> findByCategory(Category category) {
        return bookRepository.findByCategories(category);
    }

    // Find similar by categories
    public Set<Book> findSimilarByCategories(Book book) {
        Set<Book> result = new LinkedHashSet<>();
        for (Category category : book.getCategories()) {
            result.addAll(findByCategory(category));
        }
        return result;
    }

    // Find by author
    public List<Book> findByAuthor(Author author) {
        return bookRepository.findByAuthors(author);
    }

    // Find similar by authors
    public Set<Book> findSimilarByAuthors(Book book) {
        Set<Book> result = new LinkedHashSet<>();
        for (Author author : book.getAuthors()) {
            result.addAll(findByAuthor(author));
        }
        return result;
    }

    // Find all, pageable order by name
    public Page<Book> findAllPageable(Pageable pageable) {
        return bookRepository.findByOrderByNameAsc(pageable);
    }

    // Find by name, pageable order by name
    public Page<Book> findByNamePageable(String name, Pageable pageable) {
        return bookRepository.findByNameContainsIgnoreCaseOrderByNameAsc(name, pageable);
    }
}
