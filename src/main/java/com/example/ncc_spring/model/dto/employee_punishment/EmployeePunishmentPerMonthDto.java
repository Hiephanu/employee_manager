package com.example.ncc_spring.model.dto.employee_punishment;

import com.example.ncc_spring.model.entity.EmployeePunishment;
import lombok.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class EmployeePunishmentPerMonthDto {
    private long employeeId;
    List<EmployeePunishmentResDto> list;
    private YearMonth month;
    private int totalMoney;
}
