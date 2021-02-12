package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Teacher;
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
    public List<Teacher> getAllTeacher() {
        List<Teacher> teachers = new ArrayList<Teacher>();
        teacherRepo.findAll().forEach(teachers::add);

        return teachers;
    }

    @PostMapping(value = "/teacher/create")
    public Teacher postPerson(@RequestBody Teacher teacher) {
        Teacher _teacher = teacherRepo.saveAndFlush(new Teacher(teacher));

        return _teacher;
    }
    
}
