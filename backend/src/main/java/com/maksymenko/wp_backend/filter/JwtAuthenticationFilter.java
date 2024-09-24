
package com.maksymenko.wp_backend.filter;

import com.maksymenko.wp_backend.model.authentication.User;
import com.maksymenko.wp_backend.service.jwt.BlacklistedTokenService;
import com.maksymenko.wp_backend.service.jwt.JwtService;
import com.maksymenko.wp_backend.service.authentication.UserDetailsServiceIpm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final BlacklistedTokenService blacklistedTokenService;
    private final JwtService jwtService;
    private final UserDetailsServiceIpm userDetailsService;

    public JwtAuthenticationFilter(BlacklistedTokenService blacklistedTokenService, JwtService jwtService, UserDetailsServiceIpm userDetailsService) {
        this.blacklistedTokenService = blacklistedTokenService;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (blacklistedTokenService.isTokenBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String id = jwtService.extractId(token);

        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userDetailsService.loadUserById(Long.valueOf(id));

            if (jwtService.isValid(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
