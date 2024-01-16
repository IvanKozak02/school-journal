package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.pojo.*;
import com.example.demoauth.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ClassGroupService {

    private ClassRepository classRepository;

    private StudentRepository studentRepository;

    private SubjectsRepository subjectsRepository;

    private ClassGroupRepository classGroupRepository;

    private UserDataService userDataService;

    private ScheduleRepository scheduleRepository;

    public ClassGroupService(ClassRepository classRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, ClassGroupRepository classGroupRepository, UserDataService userDataService, ScheduleRepository scheduleRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.subjectsRepository = subjectsRepository;
        this.classGroupRepository = classGroupRepository;
        this.userDataService = userDataService;
        this.scheduleRepository = scheduleRepository;
    }

    public void createNewGroup(ClassGroupData classGroupData) {
        Class scClass = classRepository.findById(Long.parseLong(classGroupData.getSchoolClassId())).get();
        if (classGroupRepository.existsBySchoolClassAndNumberOfGroupAndSubject(scClass,
                classGroupData.getNumberOfGroup(),subjectsRepository.findByName(classGroupData.getSubject()))){
            ClassGroup classGroup = classGroupRepository.findBySchoolClassAndNumberOfGroupAndSubject(scClass,
                    classGroupData.getNumberOfGroup(),subjectsRepository.findByName(classGroupData.getSubject()));
            List<Student> studentsAddToGroup = studentRepository.findByUserPersonalData_IdIn(classGroupData.getStudents());
            List<Student> groupStudent = classGroup.getStudents();
            groupStudent.addAll(studentsAddToGroup);

            Comparator<Student> compareByStudentName = Comparator.comparing(s->s.getUserPersonalData().getUserName());
            groupStudent.sort(compareByStudentName);
            classGroup.setStudents(groupStudent);
            classGroupRepository.save(classGroup);
        }
        else {
            ClassGroup classGroup = new ClassGroup();
            classGroup.setNumberOfGroup(classGroupData.getNumberOfGroup());
            classGroup.setSchoolClass(classRepository.findById(Long.parseLong(classGroupData.getSchoolClassId())).get());
            List<Student> students = studentRepository.findByUserPersonalData_IdIn(classGroupData.getStudents());
            classGroup.setStudents(students);
            classGroup.setSubject(subjectsRepository.findByName(classGroupData.getSubject()));
            classGroupRepository.save(classGroup);
        }

    }

    public List<Subject> checkIfExistsGroupWithSubjects(List<Subject> subjects, String classId) {
        List<Subject> subjectsWithoutGroups = new ArrayList<>();
        for (Subject subject : subjects) {
            if (!classGroupRepository.existsBySubjectAndSchoolClass(subject, classRepository.findById(Long.parseLong(classId)).get())) {
                subjectsWithoutGroups.add(subject);
            }
        }
        return subjectsWithoutGroups;
    }

    public boolean checkIfExistsGroupsByClass(String classId) {
        return classGroupRepository.existsBySchoolClass(classRepository.findById(Long.parseLong(classId)).get());
    }

    public List<StudentsWithGroup> findStudentsToEveryGroup(SubjectAndClass subjectAndClass) {
        List<ClassGroup> groupsBySubject = classGroupRepository.findBySchoolClassAndSubject(classRepository.findById(Long.parseLong(subjectAndClass.getClassId())).get(), subjectsRepository.findByName(subjectAndClass.getSubject()));
        List<StudentsWithGroup> students = new ArrayList<>();

        for (ClassGroup classGroup : groupsBySubject) {
            students.add(new StudentsWithGroup(classGroup.getStudents(), classGroup.getNumberOfGroup()));
        }

        return students;
    }

    public void deleteGroupBySchoolClassAndSubject(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassAndSubject classAndSubject = objectMapper.readValue(jsonData,ClassAndSubject.class);
        Class scClass = classRepository.findById(Long.parseLong(classAndSubject.getClassId())).get();
        Subject subject = subjectsRepository.findByName(classAndSubject.getSubjectName());
        if (checkSchedule(scClass,subject)){
            classGroupRepository.deleteClassGroupsBySchoolClassAndSubject(scClass,subject);
        }
        else {
            throw new Exception("Для групи вже введено розклад!!!");
        }
    }

    public boolean checkSchedule(Class scClass, Subject subject){
        List<ClassGroup> classGroup = classGroupRepository.findBySchoolClassAndSubject(scClass,subject);
        for (ClassGroup group : classGroup) {
            if (scheduleRepository.existsScheduleByClassGroup(group)) {
                return false;
            }
        }
        return true;
    }


    public boolean checkSchedule(Class scClass, Subject subject,int groupNumber){
        ClassGroup classGroup = classGroupRepository.findBySchoolClassAndNumberOfGroupAndSubject(scClass, groupNumber,subject);
        return !scheduleRepository.existsScheduleByClassGroup(classGroup);
    }
    public void deleteStudentFromGroup(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteData deleteData = objectMapper.readValue(jsonData, DeleteData.class);
        Class scClass = classRepository.findById(Long.parseLong(deleteData.getClassId())).get();
        Subject subject = subjectsRepository.findByName(deleteData.getSubjectName());
        List<ClassGroup> classGroups = classGroupRepository.findBySchoolClassAndSubject(scClass, subject);
        List<String> studentsId = new ArrayList<>();
        for (int i = 0; i < deleteData.getStudents().size(); i++) {
            studentsId.add(deleteData.getStudents().get(i).getStudentId());
        }
        List<Student> students = studentRepository.findByUserPersonalData_IdIn(studentsId);

        for (Student student : students) {
            for (ClassGroup classGroup : classGroups) {
                if (classGroup.getStudents().contains(student)) {
                    List<Student> students1 = classGroup.getStudents();
                    students1.remove(student);
                    classGroup.setStudents(students1);
                    classGroupRepository.save(classGroup);
                }
            }
        }
    }

    public void deleteStudentFromAllSubGroup(Class scClass,Student student){
        List<ClassGroup> classGroups = classGroupRepository.findBySchoolClass(scClass);
        int kstOfEmptyGroups = 0;
        if (!classGroups.isEmpty()){
            for (ClassGroup classGroup : classGroups) {
                if (classGroup.getStudents().contains(student)) {
                    List<Student> students = classGroup.getStudents();
                    students.remove(student);
                    classGroup.setStudents(students);
                    classGroupRepository.save(classGroup);

                }
                if (classGroup.getStudents().isEmpty()){
                    kstOfEmptyGroups++;
                }
            }
        }
        if (kstOfEmptyGroups == classGroups.size() && kstOfEmptyGroups !=0){
            classGroupRepository.deleteAll();
        }

    }

    public void deleteSubGroup(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteSubGroupData deleteSubGroupData = objectMapper.readValue(jsonData,DeleteSubGroupData.class);
        Class scClass = classRepository.findById(Long.parseLong(deleteSubGroupData.getClassId())).get();
        Subject subject = subjectsRepository.findByName(deleteSubGroupData.getSubjectName());
        if (checkSchedule(scClass,subject,Integer.parseInt(deleteSubGroupData.getClassSubGroup()))){
            classGroupRepository.deleteByNumberOfGroupAndSchoolClassAndSubject(Integer.parseInt(deleteSubGroupData.getClassSubGroup()),
                    scClass,subject);
        }
        else {
            throw new Exception();
        }

    }

    public List<ClassGroup> getAllGroupsOfClass(String classId, String subjectName){
        Class schoolClass = classRepository.findById(Long.parseLong(classId)).get();
        Subject subject = subjectsRepository.findByName(subjectName);
        return classGroupRepository.findBySchoolClassAndSubject(schoolClass, subject);
    }
}

