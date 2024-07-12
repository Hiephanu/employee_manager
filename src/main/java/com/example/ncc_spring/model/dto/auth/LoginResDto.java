package com.example.ncc_spring.model.dto.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResDto {
    private String token;
    private String refreshToken;
    private long accountId;
    private long employeeId;
    private String employee_name;
    private String employee_photo;
}
