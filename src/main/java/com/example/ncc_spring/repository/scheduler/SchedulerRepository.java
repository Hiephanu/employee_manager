package com.example.ncc_spring.repository.scheduler;

import com.example.ncc_spring.model.entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchedulerRepository extends JpaRepository<Scheduler , Long> {
    @Query(value = "SELECT * FROM scheduler WHERE task_id = :taskId ORDER BY id LIMIT 1 OFFSET 0", nativeQuery = true)
    Optional<Scheduler> findFirstSchedulerByTaskId(@Param("taskId") Long taskId);
}
