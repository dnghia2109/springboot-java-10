package com.example.springsecurity.controller;

import com.example.springsecurity.request.LoginRequest;
import com.example.springsecurity.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String loginSuccess(@RequestBody LoginRequest request, HttpSession session) {
        return authService.login(request, session);
    }
}
