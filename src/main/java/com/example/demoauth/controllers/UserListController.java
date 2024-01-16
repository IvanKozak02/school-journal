package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class UserListController {

    private TeacherService teacherService;

    private AdministrationService administrationService;

    private StudentService studentService;

    private UserDataService userDataService;
    private final RoleRepository roleRepository;


    public UserListController(TeacherService teacherService, AdministrationService administrationService, StudentService studentService, UserDataService userDataService,
                              RoleRepository roleRepository) {
        this.teacherService = teacherService;
        this.administrationService = administrationService;
        this.studentService = studentService;
        this.userDataService = userDataService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users")
    public String getUserListPage(Authentication authentication, Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }
        return "UserList";
    }

    @GetMapping("/allteachers")
    public ResponseEntity<?> getAllTeachersBySchool(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        List<Teacher> teachers = teacherService.getAllTeachersBySchool(userPersonalData.getSchool())
                .stream().sorted(Comparator.comparing(teacher -> teacher.getUserPersonalData().getUserName())).toList();
        if (!teachers.isEmpty()) {
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(teachers,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allstudents")
    public ResponseEntity<?> getAllStudentsBySchool(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        List<Student> studentList = studentService.getAllStudentsBySchool(userPersonalData.getSchool());
        List<String> students = studentList.stream().sorted(Comparator.comparing(administration -> administration.getUserPersonalData().getUserName())).map(s->s.getUserPersonalData().getId() + "/" +
                s.getUserPersonalData().getUserName() + "/" + s.getUserPersonalData().getWorkPhone() + "/" +
                s.getUserPersonalData().getEmail() + "/" + (s.getSchoolClass() == null?"-":s.getSchoolClass().getNameOfClass()) + "/" +
                s.getParentsName() + "/" + s.getParentsPhones()).toList();

        if (!students.isEmpty()) {

            System.out.println(new ResponseEntity<>(students, HttpStatus.OK));
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(students,HttpStatus.BAD_REQUEST);
        }

    }



    @GetMapping("/alladmins")
    public ResponseEntity<?> getAllAdminsBySchool(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        List<Administration> administrations = administrationService.getAllAdminsBySchool(userPersonalData.getSchool())
                .stream().sorted(Comparator.comparing(administration -> administration.getUserPersonalData().getUserName())).toList();
        if (!administrations.isEmpty()) {
            return new ResponseEntity<>(administrations, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(administrations,HttpStatus.BAD_REQUEST);
        }
    }
}
