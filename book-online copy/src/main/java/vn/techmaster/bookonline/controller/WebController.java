package vn.techmaster.bookonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.techmaster.bookonline.dto.CartBookRequest;
import vn.techmaster.bookonline.dto.CommentRequest;
import vn.techmaster.bookonline.entity.*;
import vn.techmaster.bookonline.security.UserDetailsCustom;
import vn.techmaster.bookonline.service.*;
import vn.techmaster.bookonline.util.Utils;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class WebController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartBookService cartBookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String showHomePage(Model model) {
        model.addAttribute("books", bookService.findAll().subList(0, 4));
        return "index";
    }

    @GetMapping("/books")
    public String showBooksPage(Model model,
                                @RequestParam(value = "name", defaultValue = "") String name,
                                @RequestParam(value = "page", defaultValue = "1") Integer page) {
        if (page <= 0) {
            return "redirect:/books";
        }
        Page<Book> pageBook = bookService.findByNamePageable(name, PageRequest.of(page - 1, 8));
        Integer maxPage = pageBook.getTotalPages();
        model.addAttribute("page", page);
        model.addAttribute("maxPage", maxPage);

        model.addAttribute("name", name);

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        model.addAttribute("books", pageBook.getContent());
        return "books";
    }

    @PostMapping("/books")
    public String submitFilterBooks(RedirectAttributes redirectAttributes,
            @ModelAttribute List<Category> selectedCategories,
            @ModelAttribute Long minPrice,
            @ModelAttribute Long maxPrice,
            @ModelAttribute Integer minPages,
            @ModelAttribute Integer maxPages,
            @ModelAttribute String keyword) {
        redirectAttributes.addAttribute("categories", selectedCategories);
        redirectAttributes.addAttribute("minPrice", minPrice);
        redirectAttributes.addAttribute("maxPrice", maxPrice);
        redirectAttributes.addAttribute("minPages", minPages);
        redirectAttributes.addAttribute("maxPages", maxPages);
        redirectAttributes.addAttribute("keyword", keyword);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String showBookDetailsPage(Model model, @PathVariable("id") String id) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", Utils.showList(bookService.getCategoriesNames(book)));
        model.addAttribute("authors", Utils.showList(bookService.getAuthorsNames(book)));

        List<Comment> comments = commentService.findByBook(book);
        model.addAttribute("comments", comments);

        model.addAttribute("userService", userService);

        model.addAttribute("booksSameCategories", bookService.findSimilarByCategories(book));
        model.addAttribute("booksSameAuthors", bookService.findSimilarByAuthors(book));

        model.addAttribute("commentRequest", new CommentRequest());
        model.addAttribute("cartBookRequest", new CartBookRequest());
        return "book-details";
    }

    @PostMapping("/books/{id}/comments/add")
    public String submitAddComment(Model model,
                                  @PathVariable String id,
                                  @Valid @ModelAttribute CommentRequest commentRequest,
                                  BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("commentRequest", commentRequest);
            return "redirect:/books/" + id;
        }

        UserDetailsCustom userDetailsCustom =
                (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetailsCustom.getUser().getId());

        Book book = bookService.findById(id);

        commentService.saveByRequest(commentRequest, user, book);

        return "redirect:/books/" + id;
    }

    @PostMapping("/books/{bookId}/cartBooks/add")
    public String submitAddCartBook(Model model,
                                    @PathVariable String bookId,
                                    @Valid @ModelAttribute CartBookRequest cartBookRequest,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("dangerAlert", result.getFieldError().getDefaultMessage());
            return "redirect:/books/" + bookId;
        }

        UserDetailsCustom userDetailsCustom =
                (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetailsCustom.getUser().getId());
        Cart cart = cartService.findByUser(user);

        Book book = bookService.findById(bookId);

        cartBookService.saveByRequest(cartBookRequest, cart, book);

        redirectAttributes.addFlashAttribute("successAlert", "Items added to cart!");
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/cartBooks/{id}/delete")
    public String deleteCartBook(Model model,
                                 @PathVariable String id) {
        CartBook cartBook = cartBookService.findById(id);
        cartBookService.delete(cartBook);

        return "redirect:/checkout";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    @GetMapping("/testimonials")
    public String showTestimonialsPage() {
        return "testimonials";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        UserDetailsCustom userDetailsCustom =
                (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetailsCustom.getUser().getId());
        Cart cart = cartService.findByUser(user);
        List<CartBook> cartBooks = cartBookService.findByCart(cart);
        model.addAttribute("cartBooks", cartBooks);
        model.addAttribute("totalPrice", cartService.getTotalPrice(cart));
        return "checkout";
    }

    // TODO
    @GetMapping("/checkout/confirm")
    public String showCheckoutConfirmPage() {
        UserDetailsCustom userDetailsCustom =
                (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetailsCustom.getUser().getId());
        Cart cart = cartService.findByUser(user);
        List<CartBook> cartBooks = cartBookService.findByCart(cart);
        for (CartBook cartBook : cartBooks) {
            cartBookService.delete(cartBook);
        }
        return "checkout-confirm";
    }

    @GetMapping("/terms")
    public String showTermsPage() {
        return "terms";
    }
}
