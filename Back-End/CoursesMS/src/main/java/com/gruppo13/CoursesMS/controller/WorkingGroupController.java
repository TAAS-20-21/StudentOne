package com.gruppo13.CoursesMS.controller;

import com.gruppo13.CoursesMS.model.WorkingGroup;
import com.gruppo13.CoursesMS.repository.WorkingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class WorkingGroupController {

    @Autowired
    WorkingGroupRepository workingGroupRepo;

    @GetMapping("/working_group")
    public List<WorkingGroup> getAllCourse() {
        List<WorkingGroup> workingGroup = new ArrayList<WorkingGroup>();
        workingGroupRepo.findAll().forEach(workingGroup::add);
        return workingGroup;
    }

    @PostMapping(value = "/working_group/create")
    public WorkingGroup postCourse(@RequestBody WorkingGroup workingGroup) {
        WorkingGroup _workingGroup = workingGroupRepo.saveAndFlush(new WorkingGroup(workingGroup));
        return _workingGroup;
    }

    @GetMapping("/working_group/workingGroupByName")
    public WorkingGroup findWorkingGroupByName(String name) {
        WorkingGroup _workingGroup = new WorkingGroup();
        _workingGroup = workingGroupRepo.findByName(name);
        return _workingGroup;
    }

    @PostMapping("/working_group/workingGroupById")
    public ResponseEntity<WorkingGroup> getWorkingGroup(@RequestBody WorkingGroup wg){
        return ResponseEntity.ok(workingGroupRepo.findById(wg.getId()).get());
    }
}
