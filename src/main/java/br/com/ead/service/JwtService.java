package br.com.ead.service;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {

    String generateToken(String username);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);

    String getTokenFromRequest(HttpServletRequest request);
}
