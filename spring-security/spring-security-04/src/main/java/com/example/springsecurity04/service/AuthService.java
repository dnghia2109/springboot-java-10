package com.example.springsecurity04.service;

import com.example.springsecurity04.entity.TokenConfirm;
import com.example.springsecurity04.entity.User;
import com.example.springsecurity04.exception.BadRequestException;
import com.example.springsecurity04.exception.NotFoundException;
import com.example.springsecurity04.repository.TokenConfirmRepository;
import com.example.springsecurity04.repository.UserRepository;
import com.example.springsecurity04.request.LoginRequest;
import com.example.springsecurity04.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenConfirmRepository tokenConfirmRepository;
    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String login(LoginRequest request, HttpSession session) {
        try {
            // 1. Tạo đối tượng xác thực
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            // 2. Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);

            // 3. Lưu đối tượng vào trong context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 4. Set cookie
            session.setAttribute("MY_SESSION", authentication.getName());

            return "Đăng nhập thành công";
        }
        catch (AuthenticationException e) {
            throw new NotFoundException("Tài khoản hoặc mật khẩu không chính xác");
        }
    }


    public String logout(HttpSession session) {
        return null;
    }

    @Transactional
    public String register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getEnabled()
                    && user.getName().equals(request.getName())
                    && user.getEmail().equals(request.getEmail())
            ) {
                // Generate ra token va send mail
                // return link kích hoạt ở đây
                return generateTokenAndSendMail(user);
            }
            throw new BadRequestException("Tài khoản đã được kích hoạt");
        }

        // Tạo user mới
        User user = new User(request.getName(),
                request.getEmail(), passwordEncoder.encode(request.getPassword()), List.of("USER")
        );
        userRepository.save(user);

        return generateTokenAndSendMail(user);
    }

    public String generateTokenAndSendMail(User user) {
        // Tạo token
        String generateToken = UUID.randomUUID().toString();
        TokenConfirm tokenConfirm = new TokenConfirm(
                generateToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);

        tokenConfirmRepository.save(tokenConfirm);

        // Tạo link và send email
        String link = "http://localhost:8080/api/auth/confirm?token=" + generateToken;
        mailService.send(user.getEmail(), "Kích hoạt tài khoản", link);

        return link;
    }

    @Transactional
    public String confirm(String token) {
        Optional<TokenConfirm> tokenConfirmOptional = tokenConfirmRepository.findByToken(token);
        if(tokenConfirmOptional.isEmpty()) {
            throw new NotFoundException("Token not found");
        }

        // Kiểm tra xem token đã được kích hoạt hay chưa
        TokenConfirm tokenConfirm = tokenConfirmOptional.get();
        if(tokenConfirm.getConfirmAt() != null) {
            throw new BadRequestException("Token đã được kích hoạt");
        }

        // Kiểm tra còn thời gian hay không
        if(tokenConfirm.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token đã hết hạn");
        }

        // Set lại thời gian kích hoạt của token
        tokenConfirm.setConfirmAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);

        // Kích hoạt user
        User user = tokenConfirm.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return "confirmed";
    }
}
