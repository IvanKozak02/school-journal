package com.example.demoauth.service;

import com.example.demoauth.models.ScheduleOfBells;
import com.example.demoauth.models.School;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.ScheduleOfBellsData;
import com.example.demoauth.repository.ScheduleOfBellsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class ScheduleOfBellsService {

    private final ScheduleOfBellsRepository scheduleOfBellsRepository;

    public ScheduleOfBellsService(ScheduleOfBellsRepository scheduleOfBellsRepository) {
        this.scheduleOfBellsRepository = scheduleOfBellsRepository;
    }

    ObjectMapper objectMapper = new ObjectMapper();


    public void addNewScheduleOfBells(String jsonData, UserPersonalData userPersonalData) throws JsonProcessingException {

        if (scheduleOfBellsRepository.existsBySchool(userPersonalData.getSchool())){
            updateScheduleOfBells(jsonData,userPersonalData);
        }
        else {
            String [] data = getDataAboutSchedule(jsonData);
            ScheduleOfBells scheduleOfBells = new ScheduleOfBells();
            saveSchedule(scheduleOfBells, data, userPersonalData);
        }
    }

    public void updateScheduleOfBells(String jsonData, UserPersonalData userPersonalData) throws JsonProcessingException {
        ScheduleOfBells scheduleOfBells = scheduleOfBellsRepository.findBySchool(userPersonalData.getSchool());
        String [] data = getDataAboutSchedule(jsonData);
        saveSchedule(scheduleOfBells, data, userPersonalData);
    }

    public void saveSchedule(ScheduleOfBells scheduleOfBells, String[] data, UserPersonalData userPersonalData){
        scheduleOfBells.setNumberOfSubject(data[0]);
        scheduleOfBells.setTimeOfStart(data[1]);
        scheduleOfBells.setTimeOfFinish(data[2]);
        if (scheduleOfBellsRepository.existsBySchool(userPersonalData.getSchool())){
            scheduleOfBellsRepository.save(scheduleOfBells);
        }
       else {
            scheduleOfBells.setSchool(userPersonalData.getSchool());
            scheduleOfBellsRepository.save(scheduleOfBells);
        }
    }

    public String[] getDataAboutSchedule(String jsonData) throws JsonProcessingException {
        ScheduleOfBellsData scheduleOfBellsData = objectMapper.readValue(jsonData, ScheduleOfBellsData.class);
        String joinSubjects = String.join(",",scheduleOfBellsData.getNumberOfSubject());
        String joinDateOfStart = String.join(",", scheduleOfBellsData.getTimeOfStartSubject());
        String joinDateOfFinish = String.join(",", scheduleOfBellsData.getTimeOfFinishSubject());

        return new String[]{joinSubjects,joinDateOfStart,joinDateOfFinish};
    }


    public ScheduleOfBells getScheduleOfBells(School school){
        return scheduleOfBellsRepository.findBySchool(school);
    }
    public boolean checkIfSchoolHasSchedule(School school){
       return scheduleOfBellsRepository.existsBySchool(school);
    }
}
