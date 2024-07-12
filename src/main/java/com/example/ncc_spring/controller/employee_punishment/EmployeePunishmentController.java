package com.example.ncc_spring.controller.employee_punishment;

import com.example.ncc_spring.helper.DateFormatHelper;
import com.example.ncc_spring.helper.DateTimeFormatHelper;
import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.model.dto.employee_punishment.EmployeePunishmentPerMonthDto;
import com.example.ncc_spring.service.employee_punishment.EmployeePunishmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/punishment")
@AllArgsConstructor
public class EmployeePunishmentController {
    private final EmployeePunishmentService employeePunishmentService;

    @GetMapping("/month/{id}")
    public ResponseEntity<?> getAllPunishmentInMonth(@PathVariable long id) {
        EmployeePunishmentPerMonthDto employeePunishmentPerMonthDto = employeePunishmentService.findAllPunishmentInMonth(id);
        return  SuccessResponseHelper.createSuccessResponse(employeePunishmentPerMonthDto);
    }

    @PostMapping("/check/day")
    public ResponseEntity<?> checkEmployeeLog(@RequestParam long id,
                                              @RequestParam String localDate) {
        return  SuccessResponseHelper.createSuccessResponse(employeePunishmentService.checkEmployeePunishment(id, DateFormatHelper.formatToLocalDate(localDate)));
    }
}
