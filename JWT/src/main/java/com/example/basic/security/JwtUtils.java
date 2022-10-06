package com.example.basic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    // Token có hạn trong vòng 24 giờ kể từ thời điểm tạo, thời gian tính theo giây
    @Value("${jwt.duration}")
    private Integer duration;

    // Lấy giá trị key được cấu hình trong file appliacation.properties
    // Key này sẽ được sử dụng để mã hóa và giải mã
    @Value("${jwt.secret}")
    private String secret;

    // Sinh token
    public String generateToken(UserDetails userDetails) {
        return null;
    }

    // Lấy thông tin được lưu trong token
    public Claims getClaimsFromToken(String token) {
        return null;
    }

    // Lấy token từ trong header của request
    public String getTokenFromCookie(HttpServletRequest request) {
        return null;
    }
}
