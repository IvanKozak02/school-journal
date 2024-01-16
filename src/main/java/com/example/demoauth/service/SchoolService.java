package com.example.demoauth.service;

import com.example.demoauth.models.School;
import com.example.demoauth.models.Subject;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.SchoolData;
import com.example.demoauth.repository.SchoolRepository;
import com.example.demoauth.repository.SubjectsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepos;

    private SubjectsRepository subjectsRepository;

    public SchoolService(SchoolRepository schoolRepos, SubjectsRepository subjectsRepository) {
        this.schoolRepos = schoolRepos;
        this.subjectsRepository = subjectsRepository;
    }

    public void saveNewSchool(SchoolData schoolData, UserPersonalData userPersonalData){
        School school = new School();
        school.setName(schoolData.getName());
        school.setTypeOfSchool(schoolData.getTypeOfSchool());
        school.setCity(schoolData.getCity());
        school.setEmail(schoolData.getEmail());
        school.setOblast(schoolData.getOblast());
        school.setStreet(schoolData.getStreet());
        school.setContactPhone(schoolData.getContactPhone());
        school.setWebCite(schoolData.getWebCite());


        Set<Subject> subjectSet = getAllSubjects(schoolData.getSubjects().getSubjects());
        school.setSubjects(subjectSet);
        school.setViddil(schoolData.getViddil());
        userPersonalData.setSchool(school);
        schoolRepos.save(school);
    }


    public Set<Subject> getAllSubjects(Set<Subject> subjects){
        Set<Subject> subjectSet = new HashSet<>();
        List<Subject> subjectList = new ArrayList<>(subjects);
        for (Subject subject : subjectList) {
            if (subjectsRepository.existsByName(subject.getName())) {
                subjectSet.add(subjectsRepository.findByName(subject.getName()));
            }
        }
        return subjectSet;
    }

    public Set<School> getAll(){

        List<School> schools =  schoolRepos.findAll();
        return new HashSet<>(schools);
    }

    public School getSchool(String name){
        return schoolRepos.findByName(name);
    }

    public School getSchool(long schoolId){
        return schoolRepos.findById(schoolId).get();
    }
    public boolean checkIfExistsSchool(String name) {
        return schoolRepos.existsByName(name);
    }
}
