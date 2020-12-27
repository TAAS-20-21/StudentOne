package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Docente;
import com.gruppo13.CalendarMS.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepo;

    @GetMapping("/teacher")
    public List<Docente> getAllTeacher() {
        List<Docente> teachers = new ArrayList<Docente>();
        teacherRepo.findAll().forEach(teachers::add);

        return teachers;
    }

    @PostMapping(value = "/teacher/create")
    public Docente postPerson(@RequestBody Docente teacher) {
        Docente _teacher = teacherRepo.saveAndFlush(new Docente(teacher.getName()));

        return _teacher;
    }
}
