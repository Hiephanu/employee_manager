package com.example.ncc_spring.service.auth;

import com.example.ncc_spring.exception.ExceptionEntity.UnauthorizedException;
import com.example.ncc_spring.model.entity.AccountUsername;
import com.example.ncc_spring.repository.auth.AccountUsernameRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private AccountUsernameRepository accountUsernameRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountUsername account = accountUsernameRepository.findByUsername(username);

        if(account == null) {
            throw  new UnauthorizedException("Can not find username");
        }
        return CustomUserDetail.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+account.getA_role().name())))
                .build();
    }
}
