package com.example.ncc_spring.repository.scheduler;

import com.example.ncc_spring.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
