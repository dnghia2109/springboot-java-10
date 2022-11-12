package vn.techmaster.bookonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.techmaster.bookonline.dto.RegisterRequest;
import vn.techmaster.bookonline.dto.UserUpdateRequest;
import vn.techmaster.bookonline.entity.Cart;
import vn.techmaster.bookonline.entity.User;
import vn.techmaster.bookonline.security.UserDetailsCustom;
import vn.techmaster.bookonline.service.CartService;
import vn.techmaster.bookonline.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        UserDetailsCustom userDetailsCustom =
                (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetailsCustom.getUser().getId());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/update")
    public String showUpdateProfilePage(Model model) {
        UserDetailsCustom userDetailsCustom =
                (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetailsCustom.getUser().getId());
        UserUpdateRequest userUpdateRequest = userService.mapUserUpdateRequestEntity(user);
        model.addAttribute("userUpdateRequest", userUpdateRequest);
        return "profile-update";
    }

    @PostMapping("/profile/update")
    public String submitUpdateProfilePage(Model model,
                                          @Valid @ModelAttribute UserUpdateRequest userUpdateRequest,
                                          BindingResult result,
                                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("userUpdateRequest", userUpdateRequest);
            return "profile-update";
        }

        UserDetailsCustom userDetailsCustom =
                (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetailsCustom.getUser().getId());

        userService.saveByUserUpdateRequest(userUpdateRequest, user.getId());

        redirectAttributes.addFlashAttribute("successAlert", "Profile updated successfully!");
        return "redirect:/profile";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegister(Model model,
                                 @Valid @ModelAttribute RegisterRequest registerRequest,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("registerRequest", registerRequest);
            return "register";
        }

        User user = userService.saveByRegisterRequest(registerRequest);
        Cart cart = new Cart();
        cart.setUser(user);
        cartService.save(cart);

        redirectAttributes.addFlashAttribute("successAlert", "User register successfully!");
        return "redirect:/";
    }
}
