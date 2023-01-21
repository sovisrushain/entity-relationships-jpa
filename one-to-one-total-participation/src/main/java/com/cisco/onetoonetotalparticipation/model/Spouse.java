package com.cisco.onetoonetotalparticipation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spouse")
public class Spouse implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false, unique = true)
    private Employee employee;
}
