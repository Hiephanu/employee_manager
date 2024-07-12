package com.example.ncc_spring.repository.employee;

import com.example.ncc_spring.model.entity.EmployeeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmployeeLogRepository extends JpaRepository<EmployeeLog, Long> {
    @Query("SELECT el FROM EmployeeLog el WHERE el.employee.id = :employeeId AND DATE(el.checkIn) = DATE(:checkDate)")
    EmployeeLog findByEmployeeAndCheckIn(@Param("employeeId") long employee_id, @Param("checkDate") LocalDateTime checkDate);

    @Query("SELECT el FROM EmployeeLog el WHERE el.employee.id = :employeeId AND el.checkIn BETWEEN :startOfWeek AND :endOfWeek")
    List<EmployeeLog> findAllEmployeeLogInWeek(@Param("employeeId") long employeeId, @Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT el from EmployeeLog el WHERE el.employee.id = :id AND el.checkIn BETWEEN :startDay AND :endDay")
    List<EmployeeLog> findAllEmployeeLogBetweenDay(@Param("id") long id, @Param("startDay") LocalDateTime startDay,@Param("endDay") LocalDateTime endDay);
}
