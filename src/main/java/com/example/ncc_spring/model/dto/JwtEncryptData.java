package com.example.ncc_spring.model.dto;

import com.example.ncc_spring.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class JwtEncryptData {
    private long accountId;
    private long employeeId;
    private Role role;
}
