package com.example.ncc_spring.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scheduler {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long id;
    private String cron;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private LocalDate createdAt;
}
