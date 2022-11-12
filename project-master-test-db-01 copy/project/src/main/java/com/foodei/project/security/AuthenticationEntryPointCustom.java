package com.foodei.project.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodei.project.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointCustom implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorMessage message = new ErrorMessage(HttpStatus.UNAUTHORIZED, "Unauthorized, please login");

        System.out.println(authException.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        String messageJSON = objectMapper.writeValueAsString(message);


        response.addHeader("Content-type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(messageJSON);

//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
