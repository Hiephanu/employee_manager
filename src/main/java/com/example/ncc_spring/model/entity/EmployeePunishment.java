package com.example.ncc_spring.model.entity;

import com.example.ncc_spring.enums.PunishmentMoney;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class EmployeePunishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate date;
    private PunishmentMoney punishmentMoney;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String message;
}
