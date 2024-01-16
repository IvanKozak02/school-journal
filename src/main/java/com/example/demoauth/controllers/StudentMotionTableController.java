package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.repository.*;
import com.example.demoauth.service.StudentMotionService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class StudentMotionTableController {

    private StudentMotionService studentMotionService;

    private StudentMotionsRepository studentMotionsRepository;
    private final ClassRepository classRepository;
    private final HeadTeacherRepository headTeacherRepository;
    private final TeacherRepository teacherRepository;

    private final UserDataService userDataService;
    private final RoleRepository roleRepository;

    public StudentMotionTableController(StudentMotionService studentMotionService, StudentMotionsRepository studentMotionsRepository,
                                        ClassRepository classRepository,
                                        HeadTeacherRepository headTeacherRepository,
                                        TeacherRepository teacherRepository, UserDataService userDataService,
                                        RoleRepository roleRepository) {
        this.studentMotionService = studentMotionService;
        this.studentMotionsRepository = studentMotionsRepository;
        this.classRepository = classRepository;
        this.headTeacherRepository = headTeacherRepository;
        this.teacherRepository = teacherRepository;
        this.userDataService = userDataService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/motion-table/{classId}")
    public String getStudentMotionTable(@PathVariable long classId, Model model, Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        if (studentMotionsRepository.findByScClass_Id(classId).size() == 0){
            studentMotionService.saveDefaultValueInDB(classId);
        }
        HeadTeacher headTeacher = headTeacherRepository.findBySchoolClass(classRepository.findById(classId).get());
        if (headTeacher == headTeacherRepository.findByTeacher(teacherRepository.findByUserPersonalData(userPersonalData))){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }

        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }

        List<StudentMotions> studentMotions = studentMotionsRepository.findByScClass_Id(classId);
        model.addAttribute("class", classRepository.findById(classId).get());
        model.addAttribute("motions", studentMotions.stream().sorted((Comparator.comparing(StudentMotions::getId))).collect(Collectors.toList()));
        return "StudentMotionTable";
    }

    @PostMapping("/motion-table")
    public ResponseEntity<?> saveMotion(@RequestParam("jsonData") String jsonData){
        try {
            studentMotionService.saveMotion(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
