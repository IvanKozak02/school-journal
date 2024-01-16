package com.example.demoauth.service;

import com.example.demoauth.models.StudentMotions;
import com.example.demoauth.pojo.StudentMotionsData;
import com.example.demoauth.repository.ClassRepository;
import com.example.demoauth.repository.StudentMotionsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentMotionService {

    private StudentMotionsRepository studentMotionsRepository;
    private ClassRepository classRepository;

    public StudentMotionService(StudentMotionsRepository studentMotionsRepository, ClassRepository classRepository) {
        this.studentMotionsRepository = studentMotionsRepository;
        this.classRepository = classRepository;
    }

    public void saveMotion(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StudentMotionsData studentMotionsData = objectMapper.readValue(jsonData, StudentMotionsData.class);
        StudentMotions studentMotions = studentMotionsRepository.findByCharacteristicAndScClass_Id(studentMotionsData.getCharacteristic(),Long.parseLong(studentMotionsData.getClassId()));

        switch (studentMotionsData.getPeriod()) {
            case "I семестр" -> studentMotions.setCountFirstSem(studentMotionsData.getCountOfStudents());
            case "II семестр" -> studentMotions.setCountSecondSem(studentMotionsData.getCountOfStudents());
            case "Рік" -> studentMotions.setCountYear(studentMotionsData.getCountOfStudents());
        }
        studentMotionsRepository.save(studentMotions);
    }

    public void saveDefaultValueInDB(long classId) {

        List<StudentMotions> allStudentMotions = studentMotionsRepository.findAll();

        StudentMotions studentMotions1 = new StudentMotions("Число учнів на початок семестру","0","0","0",classRepository.findById(classId).get());
        StudentMotions studentMotions2 = new StudentMotions("Прибуло за семестр","0","0","0",classRepository.findById(classId).get());
        StudentMotions studentMotions3 = new StudentMotions("Вибуло за семестр","0","0","0",classRepository.findById(classId).get());
        StudentMotions studentMotions4 = new StudentMotions("Кількість учнів на кінець семестру","0","0","0",classRepository.findById(classId).get());
        StudentMotions studentMotions5 = new StudentMotions("Кількість учнів, що вибули до інших шкіл","0","0","0",classRepository.findById(classId).get());
        StudentMotions studentMotions6 = new StudentMotions("Вибуло з інших причин","0","0","0",classRepository.findById(classId).get());
        StudentMotions studentMotions7 = new StudentMotions("Не атестовано","0","0","0",classRepository.findById(classId).get());
        List<StudentMotions> studentMotions = List.of(studentMotions1,studentMotions2,studentMotions3,studentMotions4,studentMotions5,studentMotions6,
                studentMotions7);

        for (StudentMotions studentMotion : studentMotions) {
            studentMotionsRepository.save(studentMotion);
        }
    }
}
