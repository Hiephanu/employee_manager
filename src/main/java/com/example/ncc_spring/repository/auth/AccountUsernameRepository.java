package com.example.ncc_spring.repository.auth;

import com.example.ncc_spring.model.entity.AccountUsername;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountUsernameRepository extends JpaRepository<AccountUsername, Long> {
    AccountUsername findByUsername(String name);
}
