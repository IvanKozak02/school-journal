package com.example.demoauth.service;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.HeadTeacher;
import com.example.demoauth.models.Teacher;
import com.example.demoauth.repository.HeadTeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class HeadTeacherService {


    private HeadTeacherRepository headTeacherRepository;

    public HeadTeacherService(HeadTeacherRepository headTeacherRepository) {
        this.headTeacherRepository = headTeacherRepository;
    }


    public HeadTeacher getHeadTeacherByClass(Class schoolClass){
        return headTeacherRepository.findBySchoolClass(schoolClass);
    }

    public boolean checkIfTeacherIsHeadTeacherOfThisClass(Teacher teacherByUserPersonalData, Class scClass) {
        return headTeacherRepository.existsByTeacherAndSchoolClass(teacherByUserPersonalData,scClass);
    }
}
