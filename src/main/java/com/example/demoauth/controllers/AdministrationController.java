package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.pojo.MessageResponse;
import com.example.demoauth.pojo.ID;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.UserRepository;
import com.example.demoauth.service.AdministrationService;
import com.example.demoauth.service.TeacherService;
import com.example.demoauth.service.UserDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
public class AdministrationController {

    private AdministrationService administrationService;

    private TeacherService teacherService;

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private UserDataService userDataService;

    ObjectMapper objectMapper = new ObjectMapper();

    public AdministrationController(AdministrationService administrationService, TeacherService teacherService, RoleRepository roleRepository, UserRepository userRepository, UserDataService userDataService) {
        this.administrationService = administrationService;
        this.teacherService = teacherService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userDataService = userDataService;
    }

    @PostMapping("/updateadmin")
    public ResponseEntity<?> updateAdmin(@RequestParam("jsonData") String jsonData) {
        try {
            administrationService.updateAdmin(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/createadmin")
    public ResponseEntity<?> createAdmin(@RequestParam (name = "jsonData") String jsonData){
        try {
            ID teacherId = objectMapper.readValue(jsonData, ID.class);
            Teacher teacher = teacherService.getTeacherById(Long.parseLong(teacherId.getId()));
            UserAuthData userAuthData = teacher.getUserPersonalData().getUserAuthData();
            Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
            Role role_teacher = roleRepository.findByName(ERole.ROLE_TEACHER).get();
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role_admin);
            roleSet.add(role_teacher);
            userAuthData.setRoles(roleSet);
            userRepository.save(userAuthData);
            administrationService.createNewAdministrator(teacher.getUserPersonalData());
        }
         catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/deleteadmin")
    public ResponseEntity<?> deleteAdmin(@RequestParam("jsonData") String jsonData){
        try {
            administrationService.deleteAdmin(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveAdminAsTeacher")
    public ResponseEntity<?> saveAdminAsTeacher(@RequestParam ("jsonData") String jsonData, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        try {
            teacherService.saveAdminAsTeacher(userPersonalData, jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Помилка!!!"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageResponse("Вам надана роль вчителя!!!"),HttpStatus.OK);
    }

    @PostMapping("/addadminroleteacher")
    public ResponseEntity<?> addAdminRoleTeacher(@RequestParam("jsonData")String jsonData){
        try {
            ID id = objectMapper.readValue(jsonData,ID.class);
            UserPersonalData userPersonalData = userDataService.getUserProfile(id.getId());
            teacherService.saveAdminAsTeacher(userPersonalData,jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
