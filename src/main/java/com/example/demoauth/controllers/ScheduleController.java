package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.pojo.MessageResponse;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.service.ScheduleService;
import com.example.demoauth.service.TeacherService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ScheduleController {

    private UserDataService userDataService;

    private TeacherService teacherService;


    private ScheduleService scheduleService;
    private final RoleRepository roleRepository;

    public ScheduleController(UserDataService userDataService, TeacherService teacherService, ScheduleService scheduleService,
                              RoleRepository roleRepository) {
        this.userDataService = userDataService;
        this.teacherService = teacherService;
        this.scheduleService = scheduleService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/teacher-schedule")
    public String getTeacherSchedulePage(Authentication authentication, Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());

        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }

        School school = userPersonalData.getSchool();
        teacherService.getTeacherByUserPersonalData(userPersonalData);
        List<Subject> subjects = new ArrayList<>(school.getSubjects());
        String subjectStr = subjects.stream().map(Subject::getName).collect(Collectors.joining(","));
        model.addAttribute("subjects", subjectStr);

        return "TeacherSchedule";
    }

    @PostMapping("/teacher-schedule")
    public ResponseEntity<?> saveTeacherSchedule(@RequestParam("jsonData") String jsonData){
       try {
           scheduleService.saveSchedule(jsonData);
       }catch (Exception e){
           return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/schedulerecord")
    public ResponseEntity<?> getAllScheduleRecords(@RequestParam ("teacherId") String id){
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        Teacher teacher = teacherService.getTeacherByUserPersonalData(userPersonalData);
        List<Schedule> scheduleRec = scheduleService.getAllScheduleRecordForTeacher(teacher);
        return new ResponseEntity<>(scheduleRec, HttpStatus.OK);
    }

    @PostMapping("/edit-teacher-schedule")
    public ResponseEntity<?> editSchedule(@RequestParam("jsonData")String newSchedule, @RequestParam("oldObject") String oldSchedule){
        try {
            scheduleService.editSchedule(newSchedule, oldSchedule);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/delete-teacher-schedule")
    public ResponseEntity<?> deleteSchedule(Authentication authentication,@RequestParam("jsonData") String jsonData){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        try {
            scheduleService.deleteSchedule(jsonData, userPersonalData.getSchool());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
