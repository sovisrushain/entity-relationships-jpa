package com.query.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class StudentDto implements Serializable {
    private String name;
    private double marks;
}
