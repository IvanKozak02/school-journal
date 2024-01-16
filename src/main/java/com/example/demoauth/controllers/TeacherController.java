package com.example.demoauth.controllers;

import com.example.demoauth.models.Schedule;
import com.example.demoauth.models.Teacher;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.Subjects;
import com.example.demoauth.repository.ScheduleRepository;
import com.example.demoauth.service.TeacherService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TeacherController {

    private TeacherService teacherService;

    private UserDataService userDataService;

    private ScheduleRepository scheduleRepository;


    public TeacherController(TeacherService teacherService, UserDataService userDataService, ScheduleRepository scheduleRepository) {
        this.teacherService = teacherService;
        this.userDataService = userDataService;
        this.scheduleRepository = scheduleRepository;
    }

    @PostMapping("/updatesubjects")
    public ResponseEntity<?> updateTeacherSubjects(@RequestParam("jsonData") String jsonData) {
        try {
            teacherService.updateTeacherSubjects(jsonData);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allteacherwithoutclass")
    public ResponseEntity<?> getAllTeacherWithoutClass(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<Teacher> teachers;
        try {
            teachers = teacherService.getAllTeachersWithoutClass(userPersonalData.getSchool());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(teachers,HttpStatus.OK);
    }

    @GetMapping("/allteachersubjects")
    public ResponseEntity<?> getAllTeacherSubjects(@RequestParam("teacherId")String teacherId){
        Teacher teacher = teacherService.getTeacherByUserPersonalData(userDataService.getUserProfile(teacherId));
        return new ResponseEntity<>(teacher.getSubjects(),HttpStatus.OK);
    }

    @GetMapping("/allteachersforclass")
    public ResponseEntity<?> getAllTeachersForClass(@RequestParam long classId){
        List<Schedule> schedules = scheduleRepository.findBySchoolClass_Id(classId);
        Set<Teacher> teachers = schedules.stream().map(Schedule::getTeacher).sorted(Comparator.comparing(t->t.getUserPersonalData().getUserName())).collect(Collectors.toCollection(LinkedHashSet::new));
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

}
