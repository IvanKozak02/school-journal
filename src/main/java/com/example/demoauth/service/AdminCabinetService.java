package com.example.demoauth.service;

import com.example.demoauth.models.Class;
import com.example.demoauth.repository.AttendanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCabinetService {

    private AttendanceRecordRepository attendanceRecordRepository;

    public AdminCabinetService(AttendanceRecordRepository attendanceRecordRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    public List<String> findAttendanceRecJournal(List<Class> classes) {
        List<String> classAndJournalId = new ArrayList<>();
        for (Class aClass : classes) {
            classAndJournalId.add(aClass.getNameOfClass() + "." + attendanceRecordRepository.findBySchoolClass(aClass).getId());
        }
        return classAndJournalId;
    }
}
