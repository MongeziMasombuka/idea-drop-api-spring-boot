package com.mo.idea_drop.service;

import com.mo.idea_drop.domain.dto.auth.AuthRequest;
import com.mo.idea_drop.domain.dto.auth.AuthResponse;
import com.mo.idea_drop.domain.dto.auth.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void register(RegisterRequest req);
    AuthResponse login(AuthRequest req, HttpServletResponse response);
    AuthResponse refresh(HttpServletRequest request);
    void logout(HttpServletResponse response);
}
