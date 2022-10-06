package com.example.springsecurity04.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @GetMapping("/")
    public String getHome() {
        return "Home_page";
    }

    // Cần có ROLE là USER mới truy cập được
    @GetMapping("/profile")
    public String getProfile() {
        return "Profile_page";
    }
}
