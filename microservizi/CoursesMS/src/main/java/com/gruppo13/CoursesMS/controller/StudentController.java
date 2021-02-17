package com.gruppo13.CoursesMS.controller;

import com.gruppo13.CoursesMS.model.Student;
import com.gruppo13.CoursesMS.repository.StudentRepository;
import com.gruppo13.CoursesMS.repository.WorkingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepo;

    @Autowired
    WorkingGroupRepository wkRepo;

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

    @PostMapping(value = "/student/courses")
    public ResponseEntity<List<Long>> getCoursesById(@RequestBody Student student){
        return ResponseEntity.ok(studentRepo.getCourseIdByStudent(student.getId()));
    }

    @PostMapping(value = "/student/working_groups")
    public ResponseEntity<List<Long>> getWorkingGroupsById(@RequestBody Student student){
        return ResponseEntity.ok(wkRepo.getGroupIdByStudent(student.getId()));
    }
}
