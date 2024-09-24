package com.maksymenko.wp_backend.service.jwt;

import com.maksymenko.wp_backend.repository.jwt.BlacklistedTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TokenCleanupService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public TokenCleanupService(BlacklistedTokenRepository blacklistedTokenRepository) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60) // Runs every hour (3600000 milliseconds)
    @Transactional // Ensure the method runs within a transaction
    public void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        blacklistedTokenRepository.deleteByExpiryDateBefore(now);
        System.out.println("Deleted expired tokens at " + now);
    }
}
