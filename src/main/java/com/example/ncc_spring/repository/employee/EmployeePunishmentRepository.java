package com.example.ncc_spring.repository.employee;

import com.example.ncc_spring.model.entity.EmployeePunishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeePunishmentRepository extends JpaRepository<EmployeePunishment,Long> {
    @Query("SELECT ep FROM EmployeePunishment ep WHERE ep.employee.id = :employeeId AND ep.date BETWEEN :startDate AND :endDate")
    List<EmployeePunishment> findAllByEmployeeIdAndBetweenDate(@Param("employeeId") long employeeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<EmployeePunishment> findEmployeePunishmentByDate(LocalDate localDate);
}
