package com.o2ototal.o2ot.repository;

import com.o2ototal.o2ot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
