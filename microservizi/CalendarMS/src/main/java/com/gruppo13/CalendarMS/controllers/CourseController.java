package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Course;
import com.gruppo13.CalendarMS.repositories.CourseRepository;
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

    @GetMapping("/course")
    public List<Course> getAllCourse() {
        List<Course> course = new ArrayList<Course>();
        courseRepo.findAll().forEach(course::add);
        return course;
    }

    @PostMapping(value = "/course/create")
    public Course postCourse(@RequestBody Course course) {
        Course _courses = courseRepo.saveAndFlush(new Course(course.getName()));
        return _courses;
    }

    @GetMapping("/course/courseByName")
    public Course findCourseByName(String name) {
        Course _course = new Course();
        _course = courseRepo.findByName(name);
        return _course;
    }

    @PostMapping("/course/courseById")
    public ResponseEntity<Course> getCourse(@RequestBody Course course){
        return ResponseEntity.ok(courseRepo.findById(course.getId()).get());
    }
}