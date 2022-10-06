package com.example.basic.security;

import com.example.basic.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@AllArgsConstructor
public class AuthorizationFilterCustom extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy token từ trong header của request

        // Parse thông tin từ token

        // Kiểm tra token đã hết hạn hay chưa

        // Lấy thông tin của user dựa trên email

        // Tạo đối tượng authentication

        // Lưu vào trong context
    }
}
