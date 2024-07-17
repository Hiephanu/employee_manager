package com.example.ncc_spring.controller.auth;

import com.example.ncc_spring.model.dto.auth.RefreshTokenDto;
import com.example.ncc_spring.service.auth.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final JwtService jwtService;

    @CrossOrigin
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDto refreshToken) {
        System.out.println(refreshToken.getToken());
        return  ResponseEntity.ok(jwtService.refreshToken(refreshToken.getToken()));
    }
}
