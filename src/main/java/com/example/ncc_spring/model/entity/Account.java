package com.example.ncc_spring.model.entity;

import com.example.ncc_spring.enums.Provider;
import com.example.ncc_spring.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "account")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long a_id;
    private long a_idEmployee;
    private Provider a_provider;
    private Role a_role;
    private Date a_createdAt;
    private Date a_updatedAt;
}
