package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.pojo.*;
import com.example.demoauth.repository.ClassRepository;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.UserPersonalDataRepos;
import com.example.demoauth.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ClassGroupController {

    private UserDataService userDataService;

    private TeacherService teacherService;

    private SchoolService schoolService;

    private StudentService studentService;

    private ClassGroupService classGroupService;

    private RoleRepository roleRepository;

    private ClassRepository classRepository;

    private HeadTeacherService headTeacherService;
    ObjectMapper objectMapper = new ObjectMapper();

    public ClassGroupController(UserDataService userDataService, TeacherService teacherService, SchoolService schoolService, StudentService studentService, ClassGroupService classGroupService, RoleRepository roleRepository, ClassRepository classRepository, HeadTeacherService headTeacherService) {
        this.userDataService = userDataService;
        this.teacherService = teacherService;
        this.schoolService = schoolService;
        this.studentService = studentService;
        this.classGroupService = classGroupService;
        this.roleRepository = roleRepository;
        this.classRepository = classRepository;
        this.headTeacherService = headTeacherService;
    }

    @GetMapping("/class-groups")
    public String getClassGroupPage(@RequestParam("classId") String classId, HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);

        Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        Set<Role> roles = userPersonalData.getUserAuthData().getRoles();

        if (roles.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }

        Class scClass = classRepository.findById(Long.parseLong(classId)).get();

        if (roles.contains(role_admin)){
            model.addAttribute("approve", true);
            model.addAttribute("canCreateGroup", "YES");
        }
        else if (headTeacherService.checkIfTeacherIsHeadTeacherOfThisClass(teacherService.getTeacherByUserPersonalData(userPersonalData),scClass)){
            model.addAttribute("approve", true);
            model.addAttribute("canCreateGroup", "YES");
        }
        else {
            model.addAttribute("approve", false);
        }
        School school = userPersonalData.getSchool();
        List<Subject> subjects = new ArrayList<>(school.getSubjects());
        List<Subject> subjectsWithoutGroup = classGroupService.checkIfExistsGroupWithSubjects(subjects,classId);
        if (!subjectsWithoutGroup.isEmpty()){
            String subjectStr = subjectsWithoutGroup.stream().map(Subject::getName).collect(Collectors.joining(","));
            model.addAttribute("existSubjectsWithoutGroup", true);
            model.addAttribute("subjects", subjectStr);
        }
        else {
            model.addAttribute("existSubjectsWithoutGroup", false);
        }
        model.addAttribute("classId", classId);

        if (classGroupService.checkIfExistsGroupsByClass(classId)){
            model.addAttribute("hasGroup", true);
            List<Subject> subjectHaveGroup = subjects.stream().filter(s -> !subjectsWithoutGroup.contains(s)).toList();
                model.addAttribute("subjectBtn", subjectHaveGroup);
        }
        else {
            model.addAttribute("hasGroup", false);
        }

        return "ClassGroup";
    }

    @PostMapping("/newgroup")
    public ResponseEntity<?> createNewGroup(@RequestParam("jsonData") String jsonData) throws JsonProcessingException {
        ClassGroupData classGroupData = objectMapper.readValue(jsonData,ClassGroupData.class);
        try {
            classGroupService.createNewGroup(classGroupData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @PostMapping ("/groups")
    public ResponseEntity<?> getGroup(@RequestParam("jsonData") String jsonData) throws JsonProcessingException {
        SubjectAndClass subjectAndClass = objectMapper.readValue(jsonData,SubjectAndClass.class);
        List<StudentsWithGroup> studentsWithGroups = classGroupService.findStudentsToEveryGroup(subjectAndClass);
        List<Student> allStudents = studentService.getAllStudentsByClass(subjectAndClass.getClassId());

        List<Student> studentsWithoutGroup = new ArrayList<>();
        for (int i = 0; i < allStudents.size(); i++) {
            int k = 0;
            Student student = allStudents.get(i);
            for (int j = 0; j < studentsWithGroups.size(); j++) {
                if (studentsWithGroups.get(j).getStudents().contains(student)){
                    k++;
                }
            }
            if (k == 0){
                studentsWithoutGroup.add(student);
            }
        }
        if (!studentsWithoutGroup.isEmpty()){
            studentsWithGroups.add(new StudentsWithGroup(studentsWithoutGroup,-1));
        }

        if (!studentsWithGroups.isEmpty()){
            return new ResponseEntity<>(studentsWithGroups, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/deletegroup")
    public ResponseEntity<?> deleteGroup(@RequestParam("jsonData") String jsonData){
        try {
            classGroupService.deleteGroupBySchoolClassAndSubject(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/deletesubgroup")
    public ResponseEntity<?> deleteSubGroup(@RequestParam("jsonData") String jsonData){
        try {
            classGroupService.deleteSubGroup(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/deletestudentfromgroup")
    public ResponseEntity<?> deleteStudentFromGroup(@RequestParam("jsonData") String jsonData){
        try {
            classGroupService.deleteStudentFromGroup(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allsubgroups")
    public ResponseEntity<?> getAllSubGroupsOfClass(@RequestParam("classId") String classId,
                                                    @RequestParam("subjectName") String subjectName){
        List<ClassGroup> classGroups = classGroupService.getAllGroupsOfClass(classId,subjectName);
        return new ResponseEntity<>(classGroups,HttpStatus.OK);
    }
}
