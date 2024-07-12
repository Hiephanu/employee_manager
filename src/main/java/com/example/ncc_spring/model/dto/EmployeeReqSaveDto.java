package com.example.ncc_spring.model.dto;

import com.example.ncc_spring.enums.Position;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeReqSaveDto {
    @NotBlank(message = "Name can't be null")
    private String name;
    @NotBlank(message = "CodeCheck can't be null")
    private String codeCheck;
    @Past(message = "It must day in the past")
    private Date dob;

    private Position position;
    @NotBlank(message = "Phone number can't be null")
    private String phoneNumber;
    @Email(message = "Email must be correct format")
    private String email;
    @NotNull
    private long id_department;
    private double salary;
    private String address;
}
