package com.mo.idea_drop.service.impl;

import com.mo.idea_drop.domain.dto.auth.AuthRequest;
import com.mo.idea_drop.domain.dto.auth.AuthResponse;
import com.mo.idea_drop.domain.dto.auth.RegisterRequest;
import com.mo.idea_drop.domain.entity.User;
import com.mo.idea_drop.repository.UserRepository;
import com.mo.idea_drop.security.JwtUtils;
import com.mo.idea_drop.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(RegisterRequest req) {
        String username = req.getUsername();
        String email = req.getEmail();
        String password = req.getPassword();

        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username already exists");
        }

        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists");
        }

        User newUser = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(newUser);

    }

    @Override
    public AuthResponse login(AuthRequest req, HttpServletResponse response) {
        String email = req.getEmail();
        String password = req.getPassword();
        try{
            new UsernamePasswordAuthenticationToken(email,password);
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }

        UserDetails existingUser  = userDetailsService.loadUserByUsername(email);

        String accessToken = jwtUtils.generateToken(existingUser);

        //generate refresh token
        String refreshToken = UUID.randomUUID().toString();
        //refreshTokens.put(refreshToken, email);

        //set refresh token
        ResponseCookie cookie = ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(true)
                .secure(false) // set true in production (HTTPS)
                .sameSite("Strict")
                .path("/api/v1/auth")
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new AuthResponse(accessToken, email);
    }

    @Override
    public AuthResponse refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(Optional.ofNullable(request.getCookies())
                .orElse(new Cookie[0]))
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Refresh token missing"));

        UserDetails existingUser =  jwtUtils.validateToken(refreshToken);

        String newAccessToken = jwtUtils.generateToken(existingUser);
        String email = existingUser.getUsername();

        return new AuthResponse(newAccessToken,email);
    }

    @Override
    public void logout(HttpServletResponse response) {
        //clear cookie
        ResponseCookie cookie = ResponseCookie.from("refreshToken","")
                .httpOnly(true)
                .secure(false) // set true in production (HTTPS)
                .sameSite("Strict")
                .path("/api/v1/auth")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
