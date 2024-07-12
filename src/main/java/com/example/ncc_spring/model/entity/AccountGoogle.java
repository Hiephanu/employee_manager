package com.example.ncc_spring.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Entity(name = "account_google")
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "a_id")
@Getter
@Setter
public class AccountGoogle extends Account{
    private String  email;
}
