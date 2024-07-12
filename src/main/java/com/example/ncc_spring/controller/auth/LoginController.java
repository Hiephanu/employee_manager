package com.example.ncc_spring.controller.auth;

import com.example.ncc_spring.exception.ExceptionEntity.InternalServerErrorException;
import com.example.ncc_spring.exception.ExceptionEntity.UnauthorizedException;
import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.model.dto.LoginRequestDto;
import com.example.ncc_spring.model.dto.auth.LoginGoogleReqDto;
import com.example.ncc_spring.service.auth.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    @Autowired
    private final LoginService loginService;


    @PostMapping("/username")
    public ResponseEntity<?> loginWithUsernameAndPassword(@RequestBody LoginRequestDto loginRequestDto) {
        return SuccessResponseHelper.createSuccessResponse(loginService.login(loginRequestDto));
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody LoginGoogleReqDto loginGoogleReqDto) {
        return  SuccessResponseHelper.createSuccessResponse(loginService.loginWithGoogle(loginGoogleReqDto.getToken()));
    }
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Hello, Admin!";
    }
}
