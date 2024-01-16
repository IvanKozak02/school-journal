package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.repository.CalendarPlanningRepos;
import com.example.demoauth.repository.ClassJournalRepository;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.TeacherRepository;
import com.example.demoauth.service.CalendarPlanningService;
import com.example.demoauth.service.ClassJournalService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CalendarPlanningController {

    private CalendarPlanningRepos calendarPlanningRepos;

    private ClassJournalRepository classJournalRepository;

    private CalendarPlanningService calendarPlanningService;

    private UserDataService userDataService;
    private ClassJournalService classJournalService;
    private final RoleRepository roleRepository;
    private final TeacherRepository teacherRepository;

    public CalendarPlanningController(CalendarPlanningRepos calendarPlanningRepos, ClassJournalRepository classJournalRepository, CalendarPlanningService calendarPlanningService, UserDataService userDataService, ClassJournalService classJournalService,
                                      RoleRepository roleRepository,
                                      TeacherRepository teacherRepository) {
        this.calendarPlanningRepos = calendarPlanningRepos;
        this.classJournalRepository = classJournalRepository;
        this.calendarPlanningService = calendarPlanningService;
        this.userDataService = userDataService;
        this.classJournalService = classJournalService;
        this.roleRepository = roleRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/calendar-planning")
    public String getCalendarPlanning(@RequestParam("journalId") long journalId, Model model,
                                      Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        ClassJournal classJournal = classJournalRepository.findById(journalId).get();
        Teacher teacher = teacherRepository.findByUserPersonalData(userPersonalData);
        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get()) ||
            teacher == classJournal.getSchedule().getTeacher()){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }

        LocalDate today = LocalDate.now();
        List<String> firstSem = classJournalService.getAllDatesByDays(journalId,"I семестр",userPersonalData.getSchool());
        List<String> secondSem = classJournalService.getAllDatesByDays(journalId,"II семестр",userPersonalData.getSchool());

        String semester = calendarPlanningService.findNumberOfSemester(today, firstSem, secondSem, false);

        if (semester.equals("II семестр")){
            model.addAttribute("isFirstSem", false);
        }
        else {
            model.addAttribute("isFirstSem", true);
        }

        List<CalendarPlanning> calendarPlanningList = calendarPlanningRepos.findByClassJournalAndSemester(classJournal,semester).stream()
                .sorted(Comparator.comparing(c->c.getDateOfLesson().substring(0,2))).sorted(Comparator.comparing(c->c.getDateOfLesson().substring(3))).toList();
        model.addAttribute("journalId",journalId);
        if (!calendarPlanningList.isEmpty()){
            model.addAttribute("listIsNotEmpty", true);
            model.addAttribute("calendarPlanningList", calendarPlanningList);
        }
        else {
            model.addAttribute("listIsNotEmpty", false);
        }

        if (semester.equals("I семестр")){
            if(calendarPlanningList.size() >= firstSem.size()){
                model.addAttribute("disabled", true);
                model.addAttribute("listIsNotEmpty", false);
            }
            else {
                model.addAttribute("disabled", false);
            }
        }
        else {
            if(calendarPlanningList.size() >= secondSem.size()){
                model.addAttribute("disabled", true);
                model.addAttribute("listIsNotEmpty", false);
            }
            else {
                model.addAttribute("disabled", false);
            }
        }



        model.addAttribute("className", classJournal.getSchedule().getSchoolClass().getNameOfClass());


        if (classJournal.getSchedule().getClassGroup() != null){
            model.addAttribute("hasGroup", true);
            model.addAttribute("group", classJournal.getSchedule().getClassGroup().getNumberOfGroup());
        }
        model.addAttribute("hasGroup", false);
        model.addAttribute("subject", classJournal.getSchedule().getSubject().getName());
        return "LessonPlan";
    }

    @PostMapping("/calendar-planning")
    public ResponseEntity<?> saveCalendarPlanRec(@RequestParam("jsonData") String jsonData,
                                                 Authentication authentication){
        try {
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
            calendarPlanningService.saveCalendarPlanningRecord(jsonData, userPersonalData.getSchool());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/edit-calendar-planning")
    public ResponseEntity<?> saveCalendarPlanRec(@RequestParam("newObj") String newObj,
                                                 @RequestParam("oldObj") String oldObj,
                                                 Authentication authentication){
        try{
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
            calendarPlanningService.editCalendarPlan(oldObj,newObj, userPersonalData.getSchool());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @DeleteMapping("/calendar-planning")
    public ResponseEntity<?> deleteCalendarPlanRec(@RequestParam("journalId") long journalId,
                                                   @RequestParam("date") String date,
                                                   @RequestParam("subject") String subject){
        try {
            calendarPlanningRepos.delete(calendarPlanningRepos.findByDateOfLessonAndClassJournalAndLessonTopic(date,classJournalRepository.findById(journalId).get(),subject));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/all-calendar-records")
    public ResponseEntity<?> getAllCalRecords(@RequestParam("journalId") long id,
                                              @RequestParam("semester") String semester){
        List<CalendarPlanning> calendarPlanningList = calendarPlanningRepos.findByClassJournalAndSemester(classJournalRepository.findById(id).get(),semester).stream()
                .sorted(Comparator.comparing(c->c.getDateOfLesson().substring(0,2))).sorted(Comparator.comparing(c->c.getDateOfLesson().substring(3))).toList();
        return new ResponseEntity<>(calendarPlanningList, HttpStatus.OK);
    }

}
