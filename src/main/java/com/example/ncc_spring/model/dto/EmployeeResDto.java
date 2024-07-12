package com.example.ncc_spring.model.dto;

import com.example.ncc_spring.enums.Position;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeResDto {
    private long id;
    private String name;
    private String codeCheck;
    private Date dob;
    private Position position;
    private String phoneNumber;
    private String email;
    private long departmentId;
    private double salary;
    private String address;
}
