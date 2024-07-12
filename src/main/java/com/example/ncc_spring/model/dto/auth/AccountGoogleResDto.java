package com.example.ncc_spring.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccountGoogleResDto {
    private long id;
    private long eId;
    private String email;
}
