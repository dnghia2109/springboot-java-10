package com.example.springsecurity.security;

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

@Component
public class CustomFilter extends OncePerRequestFilter {


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy email từ trong session
        String email = (String) request.getSession().getAttribute("MY_SESSION");

        if (email == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy ra thông tin của đối tượng
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

        // Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Lưu vào trong Context
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);

    }
}
