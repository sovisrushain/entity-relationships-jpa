package com.o2m.o2m.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Transactional
@Table(name = "`order`")
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    @Id
    private String id;
    private Date date;
}
