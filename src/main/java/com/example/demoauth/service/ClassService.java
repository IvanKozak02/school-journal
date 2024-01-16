package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.pojo.ClassData;
import com.example.demoauth.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ClassService {

    ObjectMapper objectMapper = new ObjectMapper();

    private TeacherRepository teacherRepository;

    private UserPersonalDataRepos userPersonalDataRepos;

    private HeadTeacherRepository classTeacherRepository;

    private ClassRepository classRepository;

    private StudentRepository studentRepository;

    private MessageService messageService;

    private AttendanceRecordRepository attendanceRecordRepository;


    public ClassService(TeacherRepository teacherRepository, UserPersonalDataRepos userPersonalDataRepos, HeadTeacherRepository classTeacherRepository, ClassRepository classRepository, StudentRepository studentRepository, MessageService messageService, AttendanceRecordRepository attendanceRecordRepository) {
        this.teacherRepository = teacherRepository;
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.classTeacherRepository = classTeacherRepository;
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.messageService = messageService;
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    public void createNewClass(String jsonData) throws JsonProcessingException {
        ClassData classData = objectMapper.readValue(jsonData, ClassData.class);
        UserPersonalData userPersonalData = userPersonalDataRepos.findUserPersonalDataById(classData.getClassData().get(1));

        Teacher teacher = teacherRepository.findByUserPersonalData(userPersonalData);
        teacher.setClassTeacher(true);
        Class schoolClass = new Class();
        schoolClass.setNameOfClass(classData.getClassData().get(0));
        schoolClass.setSchool(teacher.getUserPersonalData().getSchool());
        schoolClass.setCabinet(classData.getClassData().get(2));
        schoolClass.setKstOfStudent(0);
        classRepository.save(schoolClass);

        HeadTeacher classTeacher = new HeadTeacher();
        classTeacher.setTeacher(teacher);

        Class schoolClass1 = classRepository.findByNameOfClassAndSchool(classData.getClassData().get(0), userPersonalData.getSchool());
//                Integer.parseInt(classData.getClassData().get(0)),userPersonalData.getSchool());

        classTeacher.setSchoolClass(schoolClass1);

        classTeacherRepository.save(classTeacher);
        teacherRepository.save(teacher);
        createNewAttendanceRecordJournal(teacher, schoolClass);
        List<UserPersonalData> userPersonalDataList = Collections.singletonList(teacher.getUserPersonalData());
        messageService.saveMessage(userPersonalDataList,"Вас призначено класним керівником " + schoolClass.getNameOfClass() + " класу");
    }

    private void createNewAttendanceRecordJournal(Teacher teacher, Class schoolClass) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setId(attendanceRecordRepository.findAll().size()+1L);
        attendanceRecord.setSchoolClass(schoolClass);
        attendanceRecord.setTeacher(teacher);
        attendanceRecordRepository.save(attendanceRecord);
    }


    public List<Class> getAllClasses(School school){
        return classRepository.findBySchool(school);
    }

    public void deleteClassById(String classId) {
        List<Student> students = studentRepository.findBySchoolClass(classRepository.findById(Long.parseLong(classId)).get());
        for (Student s:
                students) {
            s.setSchoolClass(null);
            studentRepository.save(s);
        }
        attendanceRecordRepository.deleteBySchoolClass_Id(Long.parseLong(classId));
        HeadTeacher headTeacher = classTeacherRepository.findBySchoolClass(classRepository.findById(Long.parseLong(classId)).get());
        classTeacherRepository.delete(headTeacher);
        Teacher teacher = teacherRepository.findById(headTeacher.getTeacher().getId()).get();
        teacher.setClassTeacher(false);
        teacherRepository.save(teacher);

        classRepository.deleteById(Long.parseLong(classId));




    }

    public Class getClassById(long parseLong) {
        return classRepository.findById(parseLong).get();
    }



}
