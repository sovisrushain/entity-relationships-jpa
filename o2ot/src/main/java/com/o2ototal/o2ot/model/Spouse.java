package com.o2ototal.o2ot.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "spouse")
@Transactional
@NoArgsConstructor
@AllArgsConstructor
public class Spouse implements Serializable {
    @Id
    private String id;
    private String name;
}
