package com.example.ncc_spring.model.dto.employee_log;

import com.example.ncc_spring.model.entity.EmployeeLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeLogResDto {
    private long id;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private boolean checkInLate;
    private long employeeId;


    public static EmployeeLogResDto convertToDto(EmployeeLog log) {
        EmployeeLogResDto dto = new EmployeeLogResDto();
        dto.setId(log.getId());
        dto.setCheckIn(log.getCheckIn());
        dto.setCheckOut(log.getCheckOut());
        dto.setCheckInLate(log.isCheckInLate());
        dto.setEmployeeId(log.getEmployee().getId());
        return dto;
    }
}
