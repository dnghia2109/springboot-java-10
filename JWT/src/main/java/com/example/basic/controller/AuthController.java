package com.example.basic.controller;

import com.example.basic.request.LoginRequest;
import com.example.basic.request.RegisterRequest;
import com.example.basic.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, HttpServletResponse httpServletResponse) {
        return authService.login(request, httpServletResponse);
    }

    @GetMapping("/logout-handle")
    public String login(HttpSession session) {
        return authService.logout(session);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authService.confirmToken(token);
    }
}
