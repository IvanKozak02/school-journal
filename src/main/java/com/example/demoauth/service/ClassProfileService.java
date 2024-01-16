package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.pojo.ClassProfileData;
import com.example.demoauth.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassProfileService {

    private ClassRepository classRepository;

    private HeadTeacherService headTeacherService;

    private HeadTeacherRepository headTeacherRepository;

    private TeacherService teacherService;

    private UserDataService userDataService;

    private TeacherRepository teacherRepository;

    private StudentRepository studentRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    public ClassProfileService(ClassRepository classRepository, HeadTeacherService headTeacherService, HeadTeacherRepository headTeacherRepository, TeacherService teacherService, UserDataService userDataService, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.headTeacherService = headTeacherService;
        this.headTeacherRepository = headTeacherRepository;
        this.teacherService = teacherService;
        this.userDataService = userDataService;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public void updateClassProfile(String jsonData) throws JsonProcessingException {
        ClassProfileData classProfileData = objectMapper.readValue(jsonData,ClassProfileData.class);
        Class schoolClass = classRepository.findById(classProfileData.getClassId()).get();
        schoolClass.setCabinet(classProfileData.getCabinet());
        HeadTeacher headTeacher = headTeacherService.getHeadTeacherByClass(schoolClass);
        Teacher teacher = teacherService.getTeacherById(headTeacher.getTeacher().getId());
        if (!Objects.equals(headTeacher.getTeacher().getUserPersonalData().getId(), classProfileData.getNewHeadTeacherId())){
            headTeacherRepository.delete(headTeacher);
            teacher.setClassTeacher(false);
            teacherRepository.save(teacher);
            createNewHeadTeacher(classProfileData.getNewHeadTeacherId(), schoolClass);
        }

        classRepository.save(schoolClass);
    }


    public void createNewHeadTeacher(String teacherPersonalDataId, Class schoolClass){
        HeadTeacher headTeacher = new HeadTeacher();
        UserPersonalData userPersonalData = userDataService.getUserProfile(teacherPersonalDataId);
        Teacher teacher = teacherService.getTeacherByUserPersonalData(userPersonalData);
        teacher.setClassTeacher(true);
        headTeacher.setTeacher(teacher);
        headTeacher.setSchoolClass(schoolClass);
        headTeacherRepository.save(headTeacher);
    }

    public List<Teacher> getAllTeachersByClassId(long id){
        Class schoolClass = classRepository.findById(id).get();
        List<Class> classes = new ArrayList<>();
        classes.add(schoolClass);
        return teacherRepository.findByClassesIn(classes);
    }
    public Set<Student> getAllStudentsByClassId(long id){
        Class schoolClass = classRepository.findById(id).get();
        return schoolClass.getStudents();
    }

}
