package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Docente;
import com.gruppo13.CalendarMS.models.Studente;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import com.gruppo13.CalendarMS.repositories.TeacherRepository;
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
    public List<Studente> getAllStudent() {
        List<Studente> students = new ArrayList<Studente>();
        studentRepo.findAll().forEach(students::add);

        return students;
    }

    @PostMapping(value = "/student/create")
    public Studente postPerson(@RequestBody Studente student) {
        Studente _students = studentRepo.saveAndFlush(new Studente(student.getName()));

        return _students;
    }
}
