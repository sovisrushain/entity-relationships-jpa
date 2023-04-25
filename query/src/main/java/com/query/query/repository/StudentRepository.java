package com.query.query.repository;

import com.query.query.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.marks > 3.6")
    List<Student> findAllFirstClassStudents();
}
