package com.example.ncc_spring.model.dto.employee_punishment;

import com.example.ncc_spring.enums.PunishmentMoney;
import com.example.ncc_spring.model.entity.EmployeePunishment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeePunishmentResDto {
    private  long id;
    private LocalDate localDate;
    private long eId;
    private String message;
    private PunishmentMoney punishmentMoney;

    public static EmployeePunishmentResDto convertToEmployeePunishmentResDto(EmployeePunishment employeePunishment) {
        return  EmployeePunishmentResDto.builder()
                .id(employeePunishment.getId())
                .localDate(employeePunishment.getDate())
                .eId(employeePunishment.getEmployee().getId())
                .message(employeePunishment.getMessage())
                .punishmentMoney(employeePunishment.getPunishmentMoney())
                .build();
    }
}
