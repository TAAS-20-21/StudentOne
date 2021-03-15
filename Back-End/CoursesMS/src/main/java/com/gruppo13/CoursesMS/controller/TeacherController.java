package com.gruppo13.CoursesMS.controller;

import com.gruppo13.CoursesMS.model.Teacher;
import com.gruppo13.CoursesMS.repository.TeacherRepository;
import com.gruppo13.CoursesMS.service.CourseUserServiceSAGA;
import com.gruppo13.CoursesMS.util.CourseUserRelObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepo;

    @Autowired
    CourseUserServiceSAGA courseUserService;

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

    @PostMapping(value = "/teacher/courses")
    public ResponseEntity<List<Long>> getCoursesByTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherRepo.getCourseIdByTeacher(teacher.getId()));
    }

    @PostMapping(value = "/teacher/addAssignedCourse")
    public ResponseEntity<Object> addAssignedCourse(@RequestBody CourseUserRelObject req) {
        courseUserService.createCourseUser(req);
        return ResponseEntity.ok(teacherRepo.addAssignedCourse(req.getCourseId(),req.getPersonId()));
    }

    @PostMapping(value = "/teacher/deleteAssignedCourse")
    public ResponseEntity<Object> deleteAssignedCourse(@RequestBody CourseUserRelObject req) {
        courseUserService.deleteCourseUser(req);
        return ResponseEntity.ok(teacherRepo.deleteAssignedCourse(req.getCourseId(),req.getPersonId()));
    }
}
