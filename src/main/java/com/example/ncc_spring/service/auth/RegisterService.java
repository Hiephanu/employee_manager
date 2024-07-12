package com.example.ncc_spring.service.auth;

import com.example.ncc_spring.model.entity.AccountGoogle;
import com.example.ncc_spring.model.entity.AccountUsername;
import com.example.ncc_spring.repository.auth.AccountGoogleRepository;
import com.example.ncc_spring.repository.auth.AccountUsernameRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegisterService {
    @Autowired
    private AccountUsernameRepository accountUsernameRepository;

    @Autowired
    private AccountGoogleRepository accountGoogleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountUsername registerNewUser(AccountUsername accountUsername) {
        accountUsername.setPassword(passwordEncoder.encode(accountUsername.getPassword()));
        return accountUsernameRepository.save(accountUsername);
    }

    public AccountGoogle registerNewGoogleUser(AccountGoogle accountGoogle) {
        return accountGoogleRepository.save(accountGoogle);
    }
}
