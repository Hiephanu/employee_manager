package com.example.ncc_spring.model.dto;

import com.example.ncc_spring.enums.Position;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEmployeeReqDto {
    private long id;
    private String name;
    private String codeCheck;
    private Date dob;
    private Position position;
    private String phoneNumber;
    private String email;
    private long id_department;
    private double salary;
    private String address;
}
