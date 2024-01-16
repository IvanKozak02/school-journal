package com.example.demoauth.service;

import com.example.demoauth.models.*;

import com.example.demoauth.pojo.Subjects;
import com.example.demoauth.pojo.UpdateTeacherSubjects;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.SubjectsRepository;
import com.example.demoauth.repository.TeacherRepository;
import com.example.demoauth.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TeacherService {

    private RoleRepository roleRepository;

    private SubjectsRepository subjectsRepository;

    private TeacherRepository teacherRepository;

    private UserRepository userRepository;


    public TeacherService(RoleRepository roleRepository, SubjectsRepository subjectsRepository, TeacherRepository teacherRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.subjectsRepository = subjectsRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    ObjectMapper objectMapper = new ObjectMapper();


    public void createNewTeacherFromApplication(Application application){
        Teacher teacher = new Teacher();
        teacher.setSubjects(getSubjects(application.getSubjects()));
        UserPersonalData userPersonalData = application.getUserPersonalData();
        userPersonalData.setSchool(application.getSchool());
        teacher.setUserPersonalData(userPersonalData);
        teacher.setClassTeacher(false);
        teacherRepository.save(teacher);

        Role role_teacher = roleRepository.findByName(ERole.ROLE_EMPLOYEE).get();
        Role role_employee = roleRepository.findByName(ERole.ROLE_TEACHER).get();
        Set<Role> roles = new HashSet<>();
        roles.add(role_teacher);
        roles.add(role_employee);
        application.getUserPersonalData().getUserAuthData().setRoles(roles);

    }


    public void saveAdminAsTeacher(UserPersonalData userPersonalData, String jsonData) throws JsonProcessingException {
        Teacher teacher = new Teacher();
        Role teacher_role = roleRepository.findByName(ERole.ROLE_TEACHER).get();
        Role admin_role = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        Set<Role> roles = new HashSet<>();
        roles.add(teacher_role);
        roles.add(admin_role);
        teacher.setUserPersonalData(userPersonalData);
//        userPersonalData.getUserAuthData().setRoles(roles);
        UserAuthData userAuthData = userPersonalData.getUserAuthData();
        userAuthData.setRoles(roles);
        userRepository.save(userAuthData);
        if (jsonData.contains("subjects")){
            String subjects = objectMapper.readValue(jsonData,String.class);
            teacher.setSubjects(getSubjects(subjects));
        }
        teacher.setClassTeacher(false);
        teacherRepository.save(teacher);
    }

    public Set<Subject> getSubjects(String subjects){
        List<String> sub = Stream.of(subjects.trim().split("\\s*,\\s*")).toList();
        Set<Subject> subjectSet = new HashSet<>();
        for (String s : sub) {
            if (subjectsRepository.existsByName(s)) {
                subjectSet.add(subjectsRepository.findByName(s));
            }
        }
        return subjectSet;
    }

    public Teacher getTeacherByUserPersonalData(UserPersonalData userPersonalData){
       return teacherRepository.findByUserPersonalData(userPersonalData);
    }

    public boolean existsByUserData(UserPersonalData userPersonalData) {
        return teacherRepository.existsByUserPersonalData(userPersonalData);
    }


    public List<Teacher> getAllTeachersBySchool(School school){
        return teacherRepository.findByUserPersonalDataSchool(school);
    }

    public void updateTeacherSubjects(String jsonData) throws JsonProcessingException {
        UpdateTeacherSubjects subjects = objectMapper.readValue(jsonData,UpdateTeacherSubjects.class);
        long id = Long.parseLong(subjects.getId());
        Teacher teacher = teacherRepository.findById(id).get();
        teacher.setSubjects(getSubjects(subjects.getSubjects()));
        teacherRepository.save(teacher);
    }

    public Teacher getTeacherById(long id){
        return teacherRepository.findById(id).get();
    }


    public List<Teacher> getAllTeachersWithoutClass(School school){
       List<Teacher> teachers =  teacherRepository.findByUserPersonalDataSchool(school);
       return teachers.stream().filter(t -> !t.isClassTeacher()).toList();

    }

    public List<Teacher> getAllTeachers(School school) {
        return teacherRepository.findByUserPersonalDataSchool(school);
    }
}
