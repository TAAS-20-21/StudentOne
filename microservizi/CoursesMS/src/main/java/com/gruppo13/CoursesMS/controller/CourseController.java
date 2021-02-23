package com.gruppo13.CoursesMS.controller;

import com.gruppo13.CoursesMS.model.Course;
import com.gruppo13.CoursesMS.repository.CourseRepository;
import com.gruppo13.CoursesMS.service.CourseServiceSAGA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    private CourseServiceSAGA service;

    @GetMapping("/course")
    public List<Course> getAllCourse() {
        List<Course> course = new ArrayList<Course>();
        courseRepo.findAll().forEach(course::add);
        return course;
    }

    @PostMapping(value = "/course/create")
    public Course postCourse(@RequestBody Course course) {
        Course _course = courseRepo.saveAndFlush(course);
        service.createCourse(_course);
        return _course;
    }

    @GetMapping("/course/courseByName")
    public Course findCourseByName(String name) {
        Course _course = new Course();
        _course = courseRepo.findByName(name);
        return _course;
    }

    @PostMapping("/course/courseById")
    public ResponseEntity<Course> getCourse(@RequestBody Course course){
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + courseRepo.findById(course.getId()).get());
        return ResponseEntity.ok(courseRepo.findById(course.getId()).get());
    }
}