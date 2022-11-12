package vn.techmaster.bookonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.techmaster.bookonline.dto.AuthorRequest;
import vn.techmaster.bookonline.dto.BookRequest;
import vn.techmaster.bookonline.dto.CategoryRequest;
import vn.techmaster.bookonline.dto.PublisherRequest;
import vn.techmaster.bookonline.entity.*;
import vn.techmaster.bookonline.service.*;

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private UserService userService;

    // Show authors
    @GetMapping("/authors")
    public String showAuthors(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        if (page <= 0) {
            return "redirect:/admin/authors";
        }
        Page<Author> pageAuthor = authorService.findByOrderByFullNameAsc(PageRequest.of(page - 1, 5));
        Integer maxPage = pageAuthor.getTotalPages();
        model.addAttribute("page", page);
        model.addAttribute("maxPage", maxPage);

        model.addAttribute("authors", pageAuthor.getContent());
        return "admin-authors";
    }

    // Show author details
    @GetMapping("/authors/{id}")
    public String showAuthorDetails(Model model, @PathVariable("id") String id) {
        model.addAttribute("author", authorService.findById(id));
        return "admin-author-details";
    }

    // Show add author page
    @GetMapping("/authors/add")
    public String showAddAuthor(Model model) {
        model.addAttribute("authorRequest", new AuthorRequest());
        return "admin-author-add";
    }

    // Submit add author
    @PostMapping("/authors/add")
    public String submitAddAuthor(Model model,
                                  @Valid @ModelAttribute AuthorRequest authorRequest,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (authorRequest.getYearOfBirth() != null && authorRequest.getYearOfDeath() != null && authorRequest.getYearOfDeath() <=
                authorRequest.getYearOfBirth()) {
            result.addError(new FieldError("authorRequest", "yearOfDeath", "Year of death must be greater than year of birth"));
        }

        if (result.hasErrors()) {
            model.addAttribute("authorRequest", authorRequest);
            return "admin-author-add";
        }

        authorService.saveByRequest(authorRequest);

        redirectAttributes.addFlashAttribute("successAlert", "Author added successfully!");
        return "redirect:/admin/authors";
    }

    // Show update author page
    @GetMapping("/authors/{id}/update")
    public String showUpdateAuthor(Model model, @PathVariable String id) {
        Author author = authorService.findById(id);
        model.addAttribute("authorRequest", authorService.mapRequestEntity(author));
        return "admin-author-update";
    }

    // Submit update author
    @PostMapping("/authors/{id}/update")
    public String submitUpdateAuthor(Model model, @PathVariable String id,
                                  @Valid @ModelAttribute AuthorRequest authorRequest,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (authorRequest.getYearOfBirth() != null && authorRequest.getYearOfDeath() != null && authorRequest.getYearOfDeath() <=
                authorRequest.getYearOfBirth()) {
            result.addError(new FieldError("authorRequest", "yearOfDeath", "Year of death must be greater than year of birth"));
        }

        if (result.hasErrors()) {
            model.addAttribute("authorRequest", authorRequest);
            return "admin-author-update";
        }

        authorService.saveByRequest(authorRequest);

        redirectAttributes.addFlashAttribute("successAlert", "Author updated successfully!");
        return "redirect:/admin/authors/" + id;
    }

    // Show books
    @GetMapping("/books")
    public String showBooks(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        if (page <= 0) {
            return "redirect:/admin/books";
        }
        Page<Book> pageBook = bookService.findAllPageable(PageRequest.of(page - 1, 5));
        Integer maxPage = pageBook.getTotalPages();
        model.addAttribute("page", page);
        model.addAttribute("maxPage", maxPage);

        model.addAttribute("books", pageBook.getContent());
        model.addAttribute("bookService", bookService);
        return "admin-books";
    }

    // Show book details
    @GetMapping("/books/{id}")
    public String showBookDetails(Model model, @PathVariable("id") String id) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("bookService", bookService);
        return "admin-book-details";
    }

    // Show add book page
    @GetMapping("/books/add")
    public String showAddBooks(Model model) {
        model.addAttribute("bookRequest", new BookRequest());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("publishers", publisherService.findAll());
        return "admin-book-add";
    }

    // Submit add book
    @PostMapping("/books/add")
    public String submitAddPublisher(Model model,
                                     @Valid @ModelAttribute BookRequest bookRequest,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("bookRequest", bookRequest);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("publishers", publisherService.findAll());
            return "admin-book-add";
        }

        bookService.saveByRequest(bookRequest);

        redirectAttributes.addFlashAttribute("successAlert", "Book added successfully!");
        return "redirect:/admin/books";
    }

    // Show update book page
    @GetMapping("/books/{id}/update")
    public String showUpdateBook(Model model, @PathVariable String id) {
        Book book = bookService.findById(id);
        model.addAttribute("bookRequest", bookService.mapRequestEntity(book));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("publishers", publisherService.findAll());
        return "admin-book-update";
    }

    // Submit update book
    @PostMapping("/books/{id}/update")
    public String submitUpdateBook(Model model, @PathVariable String id,
                                        @Valid @ModelAttribute BookRequest bookRequest,
                                        BindingResult result,
                                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("bookRequest", bookRequest);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("publishers", publisherService.findAll());
            return "admin-book-update";
        }

        bookService.saveByRequest(bookRequest);

        redirectAttributes.addFlashAttribute("successAlert", "Book updated successfully!");
        return "redirect:/admin/books/" + id;
    }

    // Show categories
    @GetMapping("/categories")
    public String showCategories(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        if (page <= 0) {
            return "redirect:/admin/categories";
        }
        Page<Category> pageCategory = categoryService.findByOrderByNameAsc(PageRequest.of(page - 1, 5));
        Integer maxPage = pageCategory.getTotalPages();
        model.addAttribute("page", page);
        model.addAttribute("maxPage", maxPage);

        model.addAttribute("categories", pageCategory.getContent());
        return "admin-categories";
    }

    // Show category details
    @GetMapping("/categories/{id}")
    public String showCategoryDetails(Model model, @PathVariable("id") String id) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin-category-details";
    }

    // Show add category page
    @GetMapping("/categories/add")
    public String showAddCategory(Model model) {
        model.addAttribute("categoryRequest", new CategoryRequest());
        return "admin-category-add";
    }

    // Submit add category
    @PostMapping("/categories/add")
    public String submitAddCategory(Model model,
                                  @Valid @ModelAttribute CategoryRequest categoryRequest,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categoryRequest", categoryRequest);
            return "admin-category-add";
        }

        categoryService.saveByRequest(categoryRequest);

        redirectAttributes.addFlashAttribute("successAlert", "Category added successfully!");
        return "redirect:/admin/categories";
    }

    // Show update category page
    @GetMapping("/categories/{id}/update")
    public String showUpdateCategory(Model model, @PathVariable String id) {
        Category category = categoryService.findById(id);
        model.addAttribute("categoryRequest", categoryService.mapRequestEntity(category));
        return "admin-category-update";
    }

    // Submit update category
    @PostMapping("/categories/{id}/update")
    public String submitUpdateAuthor(Model model, @PathVariable String id,
                                     @Valid @ModelAttribute CategoryRequest categoryRequest,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categoryRequest", categoryRequest);
            return "admin-category-update";
        }

        categoryService.saveByRequest(categoryRequest);

        redirectAttributes.addFlashAttribute("successAlert", "Category updated successfully!");
        return "redirect:/admin/categories/" + id;
    }

    // Show publishers
    @GetMapping("/publishers")
    public String showPublishers(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        if (page <= 0) {
            return "redirect:/admin/publishers";
        }
        Page<Publisher> pagePublisher = publisherService.findByOrderByNameAsc(PageRequest.of(page - 1, 5));
        Integer maxPage = pagePublisher.getTotalPages();
        model.addAttribute("page", page);
        model.addAttribute("maxPage", maxPage);

        model.addAttribute("publishers", pagePublisher.getContent());
        return "admin-publishers";
    }

    // Show publisher details
    @GetMapping("/publishers/{id}")
    public String showPublisherDetails(Model model, @PathVariable("id") String id) {
        model.addAttribute("publisher", publisherService.findById(id));
        return "admin-publisher-details";
    }

    // Show add publisher page
    @GetMapping("/publishers/add")
    public String showAddPublisher(Model model) {
        model.addAttribute("publisherRequest", new PublisherRequest());
        return "admin-publisher-add";
    }

    // Submit add publisher
    @PostMapping("/publishers/add")
    public String submitAddPublisher(Model model,
                                    @Valid @ModelAttribute PublisherRequest publisherRequest,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("publisherRequest", publisherRequest);
            return "admin-publisher-add";
        }

        publisherService.saveByRequest(publisherRequest);

        redirectAttributes.addFlashAttribute("successAlert", "Publisher added successfully!");
        return "redirect:/admin/publishers";
    }

    // Show update publisher page
    @GetMapping("/publishers/{id}/update")
    public String showUpdatePublisher(Model model, @PathVariable String id) {
        Publisher publisher = publisherService.findById(id);
        model.addAttribute("publisherRequest", publisherService.mapRequestEntity(publisher));
        return "admin-publisher-update";
    }

    // Submit update publisher
    @PostMapping("/publishers/{id}/update")
    public String submitUpdatePublisher(Model model, @PathVariable String id,
                                     @Valid @ModelAttribute PublisherRequest publisherRequest,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("publisherRequest", publisherRequest);
            return "admin-publisher-update";
        }

        publisherService.saveByRequest(publisherRequest);

        redirectAttributes.addFlashAttribute("successAlert", "Publisher updated successfully!");
        return "redirect:/admin/publishers/" + id;
    }

    // Show users
    @GetMapping("/users")
    public String showUsers(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        if (page <= 0) {
            return "redirect:/admin/users";
        }
        Page<User> pageUser = userService.findByOrderByUsernameAsc(PageRequest.of(page - 1, 5));
        Integer maxPage = pageUser.getTotalPages();
        model.addAttribute("page", page);
        model.addAttribute("maxPage", maxPage);

        model.addAttribute("users", pageUser.getContent());
        return "admin-users";
    }

    // Show user details
    @GetMapping("/users/{id}")
    public String showUserDetails(Model model, @PathVariable("id") String id) {
        model.addAttribute("user", userService.findById(id));
        return "admin-user-details";
    }
}
