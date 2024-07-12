package com.example.ncc_spring.controller.auth;

import com.example.ncc_spring.service.auth.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final JwtService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(String refreshToken) {
        return  ResponseEntity.ok(jwtService.refreshToken(refreshToken));
    }
}
