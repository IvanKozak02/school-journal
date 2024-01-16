package com.example.demoauth.controllers;

import com.example.demoauth.models.ERole;
import com.example.demoauth.models.Role;
import com.example.demoauth.models.ScheduleOfBells;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.ScheduleOfBellsData;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.service.ScheduleOfBellsService;
import com.example.demoauth.service.UserDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ScheduleOfBellsController {


    private ScheduleOfBellsService scheduleOfBellsService;

    private UserDataService userDataService;
    private final RoleRepository roleRepository;


    public ScheduleOfBellsController(ScheduleOfBellsService scheduleOfBellsService, UserDataService userDataService,
                                     RoleRepository roleRepository) {
        this.scheduleOfBellsService = scheduleOfBellsService;
        this.userDataService = userDataService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/addschedule")
    public ResponseEntity<?> addNewSchedule(@RequestParam("jsonData") String jsonData, HttpServletRequest request) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        try {
            scheduleOfBellsService.addNewScheduleOfBells(jsonData, userPersonalData);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/schedule")
    public String getSchedulePage(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);

        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }


        if (scheduleOfBellsService.checkIfSchoolHasSchedule(userPersonalData.getSchool())){
            ScheduleOfBells scheduleOfBells = scheduleOfBellsService.getScheduleOfBells(userPersonalData.getSchool());
            model.addAttribute("hasSchedule", true);
            model.addAttribute("numberOfSubjects", scheduleOfBells.getNumberOfSubject());
            model.addAttribute("timeOfStart", scheduleOfBells.getTimeOfStart());
            model.addAttribute("timeOfFinish", scheduleOfBells.getTimeOfFinish());
        }
        else {
            model.addAttribute("hasSchedule", false);
        }

        return "ScheduleOfSubjects";
    }

}
