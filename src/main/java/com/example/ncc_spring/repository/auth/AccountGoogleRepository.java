package com.example.ncc_spring.repository.auth;

import com.example.ncc_spring.model.entity.AccountGoogle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountGoogleRepository extends JpaRepository<AccountGoogle, Long> {
    AccountGoogle findByEmail(String email);
}
