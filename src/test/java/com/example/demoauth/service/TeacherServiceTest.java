package com.example.demoauth.service;

import com.example.demoauth.models.Subject;
import com.example.demoauth.repository.SubjectsRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @InjectMocks
    TeacherService teacherService;

    @Mock
    SubjectsRepository subjectsRepository;


    @Test
    public void testGetSubjects() {

        List<String> subjectNames = Arrays.asList("Mathematics", "Physics", "Chemistry");
        Set<Subject> expectedSubjects = new HashSet<>();
        Subject mathSubject = new Subject("Mathematics");
        Subject physicsSubject = new Subject("Physics");
        expectedSubjects.add(mathSubject);
        expectedSubjects.add(physicsSubject);
        when(subjectsRepository.existsByName(mathSubject.getName())).thenReturn(true);
        when(subjectsRepository.existsByName(physicsSubject.getName())).thenReturn(true);
        when(subjectsRepository.findByName(mathSubject.getName())).thenReturn(mathSubject);
        when(subjectsRepository.findByName(physicsSubject.getName())).thenReturn(physicsSubject);
        Set<Subject> actualSubjects = teacherService.getSubjects("Mathematics, Physics, Chemistry");
        Assertions.assertEquals(expectedSubjects, actualSubjects);
    }

    Set<Subject> createSubjectSet(){
        Set<Subject> subjectSet = new HashSet<>();
        Subject subject = new Subject("Математика");
        subjectSet.add(subject);
        return subjectSet;
    }
}