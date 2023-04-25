package com.o2ototal.o2ot.controller;

import com.o2ototal.o2ot.model.Employee;
import com.o2ototal.o2ot.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<String> saveEmployees(@RequestBody List<Employee> list) {
        employeeRepository.saveAll(list);
        return ResponseEntity.ok("Data Saved");
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> list = employeeRepository.findAll();
        return new ResponseEntity<>(list, HttpStatusCode.valueOf(200));
    }
}
