package com.example.springsecurity.service;

import com.example.springsecurity.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AuthService {


    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(LoginRequest request, HttpSession session) {
        // 1. Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // 2. Tiến hành xác thực
        Authentication authentication = authenticationManager.authenticate(token);

        // 3. Lưu đối tượng vào trong context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. Set cookie
        session.setAttribute("MY_SESSION", authentication.getName());
        return "Login success";
    }
}
