package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Student;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepo;

    @GetMapping("/student")
    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<Student>();
        studentRepo.findAll().forEach(students::add);

        return students;
    }

    @PostMapping(value = "/student/create")
    public Student postPerson(@RequestBody Student student) {
        Student _students = studentRepo.saveAndFlush(new Student(student));
        return _students;
    }
}
