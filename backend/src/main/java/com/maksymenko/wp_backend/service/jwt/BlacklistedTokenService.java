package com.maksymenko.wp_backend.service.jwt;

import com.maksymenko.wp_backend.model.security.BlacklistedToken;
import com.maksymenko.wp_backend.repository.jwt.BlacklistedTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlacklistedTokenService {

    private final JwtService jwtService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public BlacklistedTokenService(JwtService jwtService, BlacklistedTokenRepository blacklistedTokenRepository) {
        this.jwtService = jwtService;
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }

    public void blacklistToken(String token) {
        BlacklistedToken blacklistedToken = new BlacklistedToken(token, jwtService.extractExpiration(token));
        blacklistedTokenRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        Optional<BlacklistedToken> blacklistedToken = blacklistedTokenRepository.findByToken(token);
        return !blacklistedToken.isEmpty();
    }
}
