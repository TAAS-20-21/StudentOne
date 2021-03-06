package com.gruppo13.CoursesMS.controller;

import com.gruppo13.CoursesMS.model.Student;
import com.gruppo13.CoursesMS.repository.StudentRepository;
import com.gruppo13.CoursesMS.repository.WorkingGroupRepository;
import com.gruppo13.CoursesMS.service.CourseUserServiceSAGA;
import com.gruppo13.CoursesMS.util.CourseUserRelObject;
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

    @Autowired
    CourseUserServiceSAGA courseUserService;

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

    @PostMapping(value = "/student/addLikedCourse")
    public ResponseEntity<Object> addAssignedCourse(@RequestBody CourseUserRelObject req) {
        courseUserService.createCourseUser(req);
        return ResponseEntity.ok(studentRepo.addLikedCourse(req.getCourseId(),req.getPersonId()));
    }

    @PostMapping(value = "/student/deleteLikedCourse")
    public ResponseEntity<Object> deleteAssignedCourse(@RequestBody CourseUserRelObject req) {
        courseUserService.deleteCourseUser(req);
        return ResponseEntity.ok(studentRepo.deleteLikedCourse(req.getCourseId(),req.getPersonId()));
    }
}
