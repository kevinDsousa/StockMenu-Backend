package com.main.infrastructure.security;

import com.main.model.enums.UserRole;
import com.main.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.parseToken(token);
                String userId = claims.getSubject();
                String email = claims.get("email", String.class);
                String companyIdStr = claims.get("companyId", String.class);
                String roleStr = claims.get("role", String.class);
                UserRole role = roleStr != null ? UserRole.valueOf(roleStr) : null;
                UUID companyId = companyIdStr != null ? UUID.fromString(companyIdStr) : null;
                StockMenuPrincipal principal = new StockMenuPrincipal(UUID.fromString(userId), email, companyId, role);
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + (role != null ? role.name() : "USER"));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singletonList(authority));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
