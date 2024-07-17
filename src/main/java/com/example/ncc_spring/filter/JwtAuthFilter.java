package com.example.ncc_spring.filter;

import com.example.ncc_spring.enums.Provider;
import com.example.ncc_spring.exception.ExceptionEntity.UnauthorizedException;
import com.example.ncc_spring.model.dto.JwtDecryptData;
import com.example.ncc_spring.model.entity.Account;
import com.example.ncc_spring.model.entity.AccountGoogle;
import com.example.ncc_spring.model.entity.AccountUsername;
import com.example.ncc_spring.repository.auth.AccountGoogleRepository;
import com.example.ncc_spring.repository.auth.AccountRepository;
import com.example.ncc_spring.repository.auth.AccountUsernameRepository;
import com.example.ncc_spring.service.auth.CustomUserDetail;
import com.example.ncc_spring.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(request.getRequestURI().startsWith("/token")) {
            filterChain.doFilter(request, response);
            return;
        }
            String authHeader = request.getHeader("Authorization");
            String token = null;
            JwtDecryptData jwtDecryptData = new JwtDecryptData();

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                try {
                    if (jwtService.verifyToken(token) != null) {
                        jwtDecryptData = jwtService.decryptToken(token);
                    }
                } catch (Exception e) {
                    // Token verification failed
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getOutputStream().println("{ \"error\": \"" + e.getMessage() + "\" }");
                    return;
                }
            }
            if(jwtDecryptData != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<Account> account = accountRepository.findById(jwtDecryptData.getAccountId());
                if(account.isPresent()) {
                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+account.get().getA_role().name()));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtDecryptData.getAccountId(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
    }
}
