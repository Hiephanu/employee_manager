package com.example.ncc_spring.controller.auth;

import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.model.entity.AccountUsername;
import com.example.ncc_spring.service.auth.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/username")
    private ResponseEntity<?> registerWithUsername(@RequestBody AccountUsername accountUsername) {
        registerService.registerNewUser(accountUsername);
        return SuccessResponseHelper.createSuccessResponse("Register success");
    }
}
