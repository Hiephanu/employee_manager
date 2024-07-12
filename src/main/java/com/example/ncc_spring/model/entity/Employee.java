package com.example.ncc_spring.model.entity;

import com.example.ncc_spring.enums.Position;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String e_name;
    private String e_codeCheck;
    private Date e_dob;
    private Position e_position;
    private String e_phoneNumber;
    private String e_email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeLog> employeeLogs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "e_department")
    private Department department;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeePunishment> employeePunishments;

    private double e_salary;
    private String e_address;
    private LocalDate e_createdAt;
    private LocalDate e_updatedAt;

    @PrePersist
    private void createdAt(){
        this.e_createdAt =LocalDate.now();
    }
    @PreUpdate
    private void updated(){
        this.e_updatedAt =LocalDate.now();
    }
}
