package com.cisco.onetoonetotalparticipation.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @OneToOne(mappedBy = "employee", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Spouse spouse;

//    public Employee(String id, String name, String address) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//    }
//
//    public Employee(String id, String name, String address, Spouse spouse) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.spouse = spouse;
//        this.spouse.setEmployee(this);
//    }
//
//    public void setSpouse(Spouse spouse) {
//        spouse.setEmployee(this);
//        this.spouse = spouse;
//    }


}

