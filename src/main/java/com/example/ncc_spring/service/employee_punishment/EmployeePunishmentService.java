package com.example.ncc_spring.service.employee_punishment;

import com.example.ncc_spring.enums.PunishmentMoney;
import com.example.ncc_spring.helper.CompareTimeHelper;
import com.example.ncc_spring.model.dto.employee_punishment.EmployeePunishmentPerMonthDto;
import com.example.ncc_spring.model.dto.employee_punishment.EmployeePunishmentResDto;
import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.model.entity.EmployeeLog;
import com.example.ncc_spring.model.entity.EmployeePunishment;
import com.example.ncc_spring.repository.employee.EmployeeLogRepository;
import com.example.ncc_spring.repository.employee.EmployeePunishmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static com.example.ncc_spring.constants.CheckInCheckoutConstants.*;

@AllArgsConstructor
@Service
public class EmployeePunishmentService {
    private final EmployeePunishmentRepository employeePunishmentRepository;
    private final EmployeeLogRepository employeeLogRepository;
    private final EntityManager entityManager;

    @Transactional
    public EmployeePunishmentResDto checkEmployeePunishment(long employeeId, LocalDate localDate) {
        EmployeeLog employeeLog = employeeLogRepository.findByEmployeeAndCheckIn(employeeId, localDate.atStartOfDay());

//        Optional<EmployeePunishment> employeePunishmentOptional = employeePunishmentRepository.findEmployeePunishmentByDate( employeeLog.getCheckOut().toLocalDate());
        if(employeeLog == null
                || CompareTimeHelper.compareLocalDateTimeWithFixTime(employeeLog.getCheckIn(),CHECK_IN_TIME) > TIME_TO_PUNISHMENT
                && CompareTimeHelper.compareLocalDateTimeWithFixTime(employeeLog.getCheckIn(),CHECK_OUT_TIME) < -TIME_TO_PUNISHMENT) {
            EmployeePunishment employeePunishment = EmployeePunishment.builder()
                    .date(localDate)
                    .punishmentMoney(PunishmentMoney.FORGOT_CHECK_IN_AND_CHECK_OUT)
                    .employee(Employee.builder().id(employeeId).build())
                    .build();
            employeePunishment.setEmployee(entityManager.merge(employeePunishment.getEmployee()));
            return EmployeePunishmentResDto.convertToEmployeePunishmentResDto(employeePunishmentRepository.save(employeePunishment));
        } else {
            EmployeePunishment employeePunishment= EmployeePunishment.builder()
                    .date(localDate)
                    .punishmentMoney(PunishmentMoney.FORGOT_CHECK_IN_OR_CHECK_OUT)
                    .employee(Employee.builder().id(employeeId).build())
                    .build();
            employeePunishment.setEmployee(entityManager.merge(employeePunishment.getEmployee()));
            return EmployeePunishmentResDto.convertToEmployeePunishmentResDto(employeePunishmentRepository.save(employeePunishment));
        }
    }

    public EmployeePunishmentPerMonthDto findAllPunishmentInMonth(long employeeId) {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        List<EmployeePunishment> employeePunishments=  employeePunishmentRepository.findAllByEmployeeIdAndBetweenDate(employeeId, startDate, endDate);
        int totalMoney = employeePunishments.stream()
                .mapToInt(p -> p.getPunishmentMoney().getValue())
                .sum();
        return  EmployeePunishmentPerMonthDto.builder()
                .month(currentMonth)
                .employeeId(employeeId)
                .list(employeePunishments.stream().map(EmployeePunishmentResDto::convertToEmployeePunishmentResDto).toList())
                .totalMoney(totalMoney)
                .build();
    }

}
