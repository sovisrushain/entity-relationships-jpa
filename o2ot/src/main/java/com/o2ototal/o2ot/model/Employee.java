package com.o2ototal.o2ot.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Entity
@Table(name = "employee")
@Transactional
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    @Id
    private String id;
    private String name;
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "spouse_id", referencedColumnName = "id", nullable = false, unique = true)
    private Spouse spouse;
}
