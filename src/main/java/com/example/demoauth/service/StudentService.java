package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.pojo.ClassIdAndStudentId;
import com.example.demoauth.pojo.ID;
import com.example.demoauth.pojo.StudentApplication;
import com.example.demoauth.pojo.UserPersonalDataAndClassId;
import com.example.demoauth.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    private RoleRepository roleRepository;

    private StudentRepository studentRepository;

    private UserPersonalDataRepos userPersonalDataRepos;

    private ClassRepository classRepository;

    private TeacherRepository teacherRepository;

    private HeadTeacherRepository headTeacherRepository;

    private MessageService messageService;

    private ClassGroupService classGroupService;



    ObjectMapper objectMapper = new ObjectMapper();
    private final MarkRepository markRepository;


    public StudentService(RoleRepository roleRepository, StudentRepository studentRepository, UserPersonalDataRepos userPersonalDataRepos, ClassRepository classRepository, TeacherRepository teacherRepository, HeadTeacherRepository headTeacherRepository, MessageService messageService, ClassGroupService classGroupService,
                          MarkRepository markRepository) {
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.classRepository = classRepository;
        this.teacherRepository = teacherRepository;
        this.headTeacherRepository = headTeacherRepository;
        this.messageService = messageService;
        this.classGroupService = classGroupService;
        this.markRepository = markRepository;
    }

    public void createNewStudent(Application application){
        Student student = new Student();
        UserPersonalData userPersonalData = application.getUserPersonalData();
        userPersonalData.setSchool(application.getSchool());
        student.setUserPersonalData(userPersonalData);
        student.setParentsName(application.getParentsName());
        student.setParentsPhones(application.getParentPhone());
        studentRepository.save(student);
    }


    public Student getStudentByUserData(UserPersonalData userPersonalData){
       return studentRepository.findByUserPersonalData(userPersonalData);
    }

    public List<Student> getAllStudentsBySchool(School school) {
        return studentRepository.findByUserPersonalDataSchool(school);
    }

    public void addStudentToClass(String jsonData) throws JsonProcessingException {
        UserPersonalDataAndClassId userPersonalDataId = objectMapper.readValue(jsonData, UserPersonalDataAndClassId.class);
        UserPersonalData userPersonalData = userPersonalDataRepos.findUserPersonalDataById(userPersonalDataId.getUserPersonalDataId());
        Class schoolClass = classRepository.findById(Long.parseLong(userPersonalDataId.getClassId())).get();
        Student student = studentRepository.findByUserPersonalData(userPersonalData);
        Set<Student> students = schoolClass.getStudents();
        students.add(student);
        schoolClass.setStudents(students);
        schoolClass.setKstOfStudent(schoolClass.getKstOfStudent()+1);
        student.setSchoolClass(schoolClass);
        classRepository.save(schoolClass);

        List<UserPersonalData> userPersonalDataList = Collections.singletonList(student.getUserPersonalData());
        messageService.saveMessage(userPersonalDataList,"Вас додано до " + schoolClass.getNameOfClass() + " класу");
    }

    public void deleteStudentById(String jsonData, UserPersonalData userPersonalData) throws Exception {
        ClassIdAndStudentId classIdAndStudentId = objectMapper.readValue(jsonData, ClassIdAndStudentId.class);
        Teacher teacher = teacherRepository.findByUserPersonalData(userPersonalData);
        Class schoolClass = classRepository.findById(Long.parseLong(classIdAndStudentId.getClassId())).get();
        if (checkIfTeacherIsClassTeacherCurrentClass(teacher,schoolClass)){
            Student student = studentRepository.findById(Long.parseLong(classIdAndStudentId.getStudentId())).get();
            student.setSchoolClass(null);
            Set<Student> students = schoolClass.getStudents();
            students.remove(student);
            schoolClass.setStudents(students);
            studentRepository.save(student);
            schoolClass.setKstOfStudent(schoolClass.getKstOfStudent()-1);
            classRepository.save(schoolClass);
            classGroupService.deleteStudentFromAllSubGroup(schoolClass,student);
            markRepository.deleteByStudent(studentRepository.findById(Long.parseLong(classIdAndStudentId.getStudentId())).get());
        }
        else {
            throw new Exception();
        }

    }


    public boolean checkIfTeacherIsClassTeacherCurrentClass(Teacher teacher, Class schoolClass){
        return headTeacherRepository.existsByTeacherAndSchoolClass(teacher, schoolClass);
    }

    public List<Student> getAllStudentsByClass(String classId) {
        Class schoolClass = classRepository.findById(Long.parseLong(classId)).get();
        return studentRepository.findBySchoolClass(schoolClass);
    }
}
