package com.query.query.controller;

import com.query.query.dto.StudentDto;
import com.query.query.model.Student;
import com.query.query.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @PostMapping
    public String saveStudentData(@RequestBody StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setMarks(studentDto.getMarks());
        studentRepository.save(student);
        return "Saved";
    }

    @GetMapping("/first-class")
    public ResponseEntity<List<Student>> getAllFirstClassStudents() {
        List<Student> list = studentRepository.findAllFirstClassStudents();
        return new ResponseEntity<>(list, HttpStatusCode.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> list = studentRepository.findAll();
        return new ResponseEntity<>(list, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{marks}")
    public ResponseEntity<List<Student>> findSpecificSet(@PathVariable String marks) {
        List<Student> list = studentRepository.findSpecific(marks);
        return new ResponseEntity<>(list, HttpStatusCode.valueOf(200));
    }
}
