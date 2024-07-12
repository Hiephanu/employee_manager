package com.example.ncc_spring.service.employee_log;

import com.example.ncc_spring.exception.ExceptionEntity.BadRequestException;
import com.example.ncc_spring.exception.ExceptionEntity.NotFoundException;
import com.example.ncc_spring.helper.DateFormatHelper;
import com.example.ncc_spring.model.dto.employee_log.EmployeeLogResDto;
import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.model.entity.EmployeeLog;
import com.example.ncc_spring.repository.employee.EmployeeLogRepository;
import com.example.ncc_spring.repository.employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeLogService {
    private final EmployeeLogRepository employeeLogRepository;
    private final EmployeeRepository employeeRepository;
    public EmployeeLogResDto getEmployeeLogOnDay(LocalDateTime localDateTime, long employeeId) {
        EmployeeLog employeeLog = employeeLogRepository.findByEmployeeAndCheckIn(employeeId, localDateTime);
        if(employeeLog == null) {
            return null;
        }
        return EmployeeLogResDto.convertToDto(employeeLog);
    }

    public boolean checkLog(long employedId) {
        LocalDateTime now = LocalDateTime.now();

        EmployeeLogResDto log = getEmployeeLogOnDay(now, employedId);
        System.out.println("log" + log);

        if(log == null) {
            return false;
        } else  {
            return log.getCheckOut() == null || log.getCheckIn() == null;
        }
    }

    @Transactional
    public void recordCheckInCheckOut(LocalDateTime scanTime, long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isPresent()) {

            EmployeeLog existingLog = employeeLogRepository.findByEmployeeAndCheckIn(employeeId, scanTime);

            if (existingLog == null) {
                EmployeeLog newLog = new EmployeeLog();
                newLog.setEmployee(Employee.builder().id(employeeId).build());
                newLog.setCheckIn(scanTime);

                employeeLogRepository.save(newLog);
            } else {
                if (existingLog.getCheckOut() == null) {
                    existingLog.setCheckOut(scanTime);
                    employeeLogRepository.save(existingLog);
                } else {
                    if (scanTime.isBefore(existingLog.getCheckIn())) {
                        existingLog.setCheckIn(scanTime);
                    }
                    else if (scanTime.isAfter(existingLog.getCheckOut())) {
                        existingLog.setCheckOut(scanTime);
                    }
                    employeeLogRepository.save(existingLog);
                }
            }
        } else {
            throw new BadRequestException("Employee not exist");
        }
    }

    public Map<LocalDate, EmployeeLogResDto> getLogInWeek(long id, String dateString) {
        LocalDate date = DateFormatHelper.formatToLocalDate(dateString);
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endOfWeekDateTime = endOfWeek.atTime(LocalTime.MAX);

        List<EmployeeLog> employeeLogs = employeeLogRepository.findAllEmployeeLogInWeek(id, startOfWeekDateTime, endOfWeekDateTime);

        Map<LocalDate, EmployeeLogResDto> result = new HashMap<>();
        for (LocalDate d = startOfWeek; !d.isAfter(endOfWeek); d = d.plusDays(1)) {
            result.put(d, null);
        }

        for (EmployeeLog log : employeeLogs) {
            LocalDate logDate = log.getCheckIn().toLocalDate();
            result.put(logDate, EmployeeLogResDto.convertToDto(log));
        }

        return result;
    }

    public Map<LocalDate, EmployeeLogResDto> getLogBetweenDate(long id, String startDate, String endDate) {
        LocalDateTime startDateTime = DateFormatHelper.formatToLocalDate(startDate).atStartOfDay();
        LocalDateTime endDateTime = DateFormatHelper.formatToLocalDate(endDate).atTime(LocalTime.MAX);

        System.out.println(startDateTime);
        System.out.println(endDateTime);

        List<EmployeeLog> employeeLogs = employeeLogRepository.findAllEmployeeLogBetweenDay(id, startDateTime, endDateTime);

        Map<LocalDate, EmployeeLogResDto> result = new HashMap<>();

        LocalDate start = startDateTime.toLocalDate();
        LocalDate end = endDateTime.toLocalDate();

        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            result.put(d, null);
        }

        for (EmployeeLog log : employeeLogs) {
            LocalDate logDate = log.getCheckIn().toLocalDate();
            result.put(logDate, EmployeeLogResDto.convertToDto(log));
        }

        return result;
    }
}
