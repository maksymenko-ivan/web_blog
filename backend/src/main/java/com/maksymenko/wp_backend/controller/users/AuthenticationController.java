package com.maksymenko.wp_backend.controller.users;

import com.maksymenko.wp_backend.model.authentication.AuthenticationResponse;
import com.maksymenko.wp_backend.model.authentication.User;
import com.maksymenko.wp_backend.service.authentication.AuthenticationService;
import com.maksymenko.wp_backend.service.jwt.BlacklistedTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final BlacklistedTokenService blacklistedTokenService;

    public AuthenticationController(AuthenticationService authService, BlacklistedTokenService blacklistedTokenService) {
        this.authService = authService;
        this.blacklistedTokenService = blacklistedTokenService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/auth")
    public ResponseEntity<String> validateToken(@AuthenticationPrincipal String token) {
        try{
            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        blacklistedTokenService.blacklistToken(token);

        return ResponseEntity.ok("Token was added to blacklist");
    }
}
