package com.example.ncc_spring.model.dto.employee_log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanTimeReqDto {
    private long id;
    private LocalDateTime localDateTime;
}
