package com.example.demoauth.service;

import com.example.demoauth.models.StructureOfYear;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.Semester;
import com.example.demoauth.repository.StructureOfYearRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class StructureService {

    ObjectMapper objectMapper = new ObjectMapper();

    private UserDataService userDataService;

    private StructureOfYearRepository structureOfYearRepository;

    public StructureService(UserDataService userDataService, StructureOfYearRepository structureOfYearRepository) {
        this.userDataService = userDataService;
        this.structureOfYearRepository = structureOfYearRepository;
    }

    public void saveStructure(String firstSem, String secondSem, HttpServletRequest request) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);

        Semester firstSemester = objectMapper.readValue(firstSem,Semester.class);
        Semester secondSemester = objectMapper.readValue(secondSem,Semester.class);

        if (!structureOfYearRepository.findAll().isEmpty()){
            structureOfYearRepository.deleteAll();
        }

        StructureOfYear structureOfYearFirstSem = new StructureOfYear();
        structureOfYearFirstSem.setStartOfSem(firstSemester.getSemStart());
        structureOfYearFirstSem.setFinishOfSem(firstSemester.getSemFinish());
        structureOfYearFirstSem.setSchool(userPersonalData.getSchool());
        structureOfYearFirstSem.setNameOfSem(firstSemester.getNameOfSem());

        structureOfYearRepository.save(structureOfYearFirstSem);

        StructureOfYear structureOfYearSecondSem = new StructureOfYear();
        structureOfYearSecondSem.setStartOfSem(secondSemester.getSemStart());
        structureOfYearSecondSem.setFinishOfSem(secondSemester.getSemFinish());
        structureOfYearSecondSem.setSchool(userPersonalData.getSchool());
        structureOfYearSecondSem.setNameOfSem(secondSemester.getNameOfSem());

        structureOfYearRepository.save(structureOfYearSecondSem);
    }

    public List<StructureOfYear> getStructure() {
        return structureOfYearRepository.findAll();
    }
}
