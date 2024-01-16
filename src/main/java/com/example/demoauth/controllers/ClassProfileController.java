package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.repository.AttendanceRecordRepository;
import com.example.demoauth.repository.ClassRepository;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.service.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ClassProfileController {

    private ClassService classService;

    private HeadTeacherService headTeacherService;

    private TeacherService teacherService;

    private ClassProfileService classProfileService;

    private RoleRepository roleRepository;

    private UserDataService userDataService;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final ClassRepository classRepository;


    public ClassProfileController(ClassService classService, HeadTeacherService headTeacherService, TeacherService teacherService, ClassProfileService classProfileService, RoleRepository roleRepository, UserDataService userDataService,
                                  AttendanceRecordRepository attendanceRecordRepository,
                                  ClassRepository classRepository) {
        this.classService = classService;
        this.headTeacherService = headTeacherService;
        this.teacherService = teacherService;
        this.classProfileService = classProfileService;
        this.roleRepository = roleRepository;
        this.userDataService = userDataService;
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.classRepository = classRepository;
    }

    @GetMapping("/classprofile")
    public String getClassProfilePage(@RequestParam(name = "classId") String classId, Model model, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findBySchoolClass(classRepository.findById(Long.parseLong(classId)).get());
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        Class schoolClass = classService.getClassById(Long.parseLong(classId));
        model.addAttribute("kstOfStudents", schoolClass.getKstOfStudent());
        model.addAttribute("cabinet", schoolClass.getCabinet());
        HeadTeacher headTeacher = headTeacherService.getHeadTeacherByClass(schoolClass);
        Teacher teacher = teacherService.getTeacherById(headTeacher.getTeacher().getId());
        Set<Role> roles = userPersonalData.getUserAuthData().getRoles();
        Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        model.addAttribute("journalId", attendanceRecord.getId());
        if (roles.contains(role_admin)){
            model.addAttribute("role", "YES");
        }
        else {
            model.addAttribute("role", "NO");
        }
        if (roles.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }

        model.addAttribute("teacherName", teacher.getUserPersonalData().getUserName());
        model.addAttribute("email", teacher.getUserPersonalData().getEmail());
        model.addAttribute("className", schoolClass.getNameOfClass());
        model.addAttribute("teacherProfileId", teacher.getUserPersonalData().getId());
        model.addAttribute("classId", schoolClass.getId());
        return "ClassProfile";
    }

    @PostMapping("/updateclassprofile")
    public ResponseEntity<?> updateClassProfile(@RequestParam (name = "jsonData") String jsonData){
        try {
            classProfileService.updateClassProfile(jsonData);
        }
        catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/allclassteachers")
    public ResponseEntity<?> getAllTeachersByClassId(@RequestParam(name = "classId") String classId){
        Collection<Teacher> teachers;
        try {
            teachers = classProfileService.getAllTeachersByClassId(Long.parseLong(classId)).stream().sorted(Comparator.comparing(t->t.getUserPersonalData().getUserName())).collect(Collectors.toCollection(LinkedHashSet::new));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(teachers,HttpStatus.OK);
    }

    @GetMapping("/allclassstudents")
    public ResponseEntity<?> getAllTeachersByClass(@RequestParam(name = "classId") String classId){
        Set<Student> students;
        try {
            students = classProfileService.getAllStudentsByClassId(Long.parseLong(classId)).stream().sorted(Comparator.comparing(s->s.getUserPersonalData().getUserName())).collect(Collectors.toCollection(LinkedHashSet::new));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(students,HttpStatus.OK);
    }
}
