package com.example.springsecurity.controller;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AdminController {

    // Cần role EDITOR (authenticated) mới có thể vào
    @GetMapping("/admin/blogs")
    public String getBlog(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "blog";
    }

    // Cần role ADMIN (authenticated) mới có thể vào
    @GetMapping("/admin/users")
    public String getUser(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }

}
