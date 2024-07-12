package com.example.ncc_spring.controller.employee_log;

import com.example.ncc_spring.helper.DateFormatHelper;
import com.example.ncc_spring.helper.DateTimeFormatHelper;
import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.model.dto.employee_log.ScanTimeReqDto;
import com.example.ncc_spring.service.employee_log.EmployeeLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/employee-log")
@AllArgsConstructor
public class EmployeeLogController {
    private final EmployeeLogService employeeLogService;

    @PostMapping("/scan")
    private ResponseEntity<?> scanTime(@RequestBody ScanTimeReqDto scanTimeReqDto) {
        if(scanTimeReqDto.getLocalDateTime() == null) {
            scanTimeReqDto.setLocalDateTime(LocalDateTime.now());
        }
        System.out.println(LocalDateTime.now());
        employeeLogService.recordCheckInCheckOut(scanTimeReqDto.getLocalDateTime(), scanTimeReqDto.getId());
        return  SuccessResponseHelper.createSuccessResponse("Scan success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDayScan(@PathVariable long id,
                                        @RequestParam(defaultValue = "") String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (Objects.equals(dateTime, "")) {
            localDateTime = DateTimeFormatHelper.formatToLocalDatetime(dateTime);
        }
        return SuccessResponseHelper.createSuccessResponse(employeeLogService.getEmployeeLogOnDay(localDateTime, id));
    }

    @GetMapping("/week/{id}")
    public ResponseEntity<?> getScanInWeek(@PathVariable long id,
                                        @RequestParam(defaultValue = "") String dateTime) {
        System.out.println(dateTime);
        if (Objects.equals(dateTime, "")) {
            dateTime = DateFormatHelper.formatToString(LocalDate.now());
        }
        return SuccessResponseHelper.createSuccessResponse(employeeLogService.getLogInWeek(id, dateTime));
    }

    @GetMapping("/range/{id}")
    public ResponseEntity<?> getScanBetweenRange(@PathVariable long id,
                                                 @RequestParam String startDate,
                                                 @RequestParam String endDate) {
        return SuccessResponseHelper.createSuccessResponse(employeeLogService.getLogBetweenDate(id, startDate, endDate));
    }

}
