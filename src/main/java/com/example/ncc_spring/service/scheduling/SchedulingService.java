package com.example.ncc_spring.service.scheduling;

import com.example.ncc_spring.model.dto.mail.MailDataDto;
import com.example.ncc_spring.model.entity.Employee;
import com.example.ncc_spring.service.email.EmailService;
import com.example.ncc_spring.service.employee.EmployeeCrudService;
import com.example.ncc_spring.service.employee_log.EmployeeLogService;
import com.example.ncc_spring.service.employee_punishment.EmployeePunishmentService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SchedulingService {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final EmployeeLogService employeeLogService;
    private final EmployeeCrudService employeeCrudService;
    private final EmployeePunishmentService employeePunishmentService;
    private final EmailService emailService;

//    @Scheduled(cron = "0 12 16 * * ?")
    public void reportTime() {
        System.out.println("HiHi");
//        List<Employee> employees = employeeCrudService.getAllEmployeeNotPagination();
//        employees.forEach(employee -> {
//            if(!employeeLogService.checkLog(employee.getId())) {
//                    MailDataDto mailDataDto = new MailDataDto();
//                    mailDataDto.setTo("doanvanhiep230903@gmail.com");
//                    mailDataDto.setSubject("Check in and check out notification");
//                    mailDataDto.setLocale("VN");
//                    mailDataDto.setCountry("Vn");
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("name", "Bạn chưa check in hay check out hãy ra quầy tiếp tân để làm thủ tục xin cảm ơn;");
//                    map.put("content", "Bạn sẽ bị phạt 50k vì lỗi quên ckeckin và checkout");
//                    mailDataDto.setProps(map);
//                     emailService.sendHtmlMail(mailDataDto, "email-template.html");
//                }
//            }
//        );
    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void commitFault() {
        List<Employee> employees = employeeCrudService.getAllEmployeeNotPagination();
        employees.forEach(employee -> {
            employeePunishmentService.checkEmployeePunishment(employee.getId(), LocalDate.now());
        });
    }
}
