package com.example.ncc_spring.service.auth;

import com.example.ncc_spring.model.entity.AccountGoogle;
import com.example.ncc_spring.repository.auth.AccountGoogleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountGoogleRepository accountGoogleRepository;

    public AccountGoogle getAccountByEmail(String email) {
        return accountGoogleRepository.findByEmail(email);
    }
}
