package com.example.ncc_spring.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String e_name;
    private String e_description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Employee> employees;

    private Date e_createdAt;
    private Date e_updatedAt;
}
