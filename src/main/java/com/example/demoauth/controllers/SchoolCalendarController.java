package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.pojo.ID;
import com.example.demoauth.pojo.SchoolCalendarData;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.UserPersonalDataRepos;
import com.example.demoauth.service.SchoolCalendarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SchoolCalendarController {

    ObjectMapper objectMapper = new ObjectMapper();

    private SchoolCalendarService schoolCalendarService;

    private UserPersonalDataRepos userPersonalDataRepos;

    private RoleRepository roleRepository;

    public SchoolCalendarController(SchoolCalendarService schoolCalendarService, UserPersonalDataRepos userPersonalDataRepos, RoleRepository roleRepository) {
        this.schoolCalendarService = schoolCalendarService;
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/school-calendar")
    public String getCalendarPage(Model model, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userPersonalDataRepos.findUserPersonalDataById(id);
        List<SchoolCalendar> holidaysEvents = schoolCalendarService.getHolidaysEvents(userPersonalData.getSchool());
        List<SchoolCalendar> schoolEvents = schoolCalendarService.getSchoolEvents(userPersonalData.getSchool());
        List<SchoolCalendar> publicEvents = schoolCalendarService.getPublicEvents(userPersonalData.getSchool());
        if (!holidaysEvents.isEmpty()){
            model.addAttribute("hasHolidaysEvents", true);
            model.addAttribute("holidayEvents", holidaysEvents);
        }
        else {
            model.addAttribute("hasHolidaysEvents", false);
        }
        if (!schoolEvents.isEmpty()){
            model.addAttribute("hasSchoolEvents", true);
            model.addAttribute("schoolEvents", schoolEvents);
        }
        else {
            model.addAttribute("hasSchoolEvents", false);
        }
        if (!publicEvents.isEmpty()){
            model.addAttribute("hasPublicEvents", true);
            model.addAttribute("publicEvents", publicEvents);
        }
        else {
            model.addAttribute("hasPublicEvents", false);
        }


        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());

        Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        if (roleSet.contains(role_admin)){
            model.addAttribute("role", true);
        }
        else {
            model.addAttribute("role", false);
        }

        return "SchoolCalendar";
    }

    @PostMapping("addschoolcalendarevents")
    public ResponseEntity<?> addSchoolCalendarEvents(@RequestParam("jsonData") String jsonData, HttpServletRequest request){
        try {
            SchoolCalendarData schoolCalendar = objectMapper.readValue(jsonData,SchoolCalendarData.class);
            schoolCalendarService.createNewSchoolCalendarEvents(schoolCalendar, request);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("updateevent")
    public ResponseEntity<?> updateEvent(@RequestParam("jsonData") String jsonData){
        try {
            SchoolCalendarData schoolCalendar = objectMapper.readValue(jsonData,SchoolCalendarData.class);
            schoolCalendarService.updateEvent(schoolCalendar.getId(), schoolCalendar);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("school-event")
    public ResponseEntity<?> deleteSchoolEvent(@RequestParam("eventId") String eventId){
        try {
            schoolCalendarService.deleteEventByID(eventId);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/school-events")
    public ResponseEntity<?> getAllEvents(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userPersonalDataRepos.findUserPersonalDataById(id);
        List<SchoolCalendar> events = schoolCalendarService.getAllEvents(userPersonalData.getSchool());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
