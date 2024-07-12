package com.example.ncc_spring.model.dto;

import com.example.ncc_spring.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
    private long e_id;
    private Role role;
    private long username;
    private long password;
}
