package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.pojo.AdditionalJournalColumnDTO;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class JournalController {

    private ClassJournalService classJournalService;

    private UserDataService userDataService;

    private CalendarPlanningService calendarPlanningService;
    private final ClassJournalRepository classJournalRepository;
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;

    public JournalController(ClassJournalService classJournalService, UserDataService userDataService, CalendarPlanningService calendarPlanningService,
                             ClassJournalRepository classJournalRepository,
                             TeacherRepository teacherRepository,
                             RoleRepository roleRepository) {
        this.classJournalService = classJournalService;
        this.userDataService = userDataService;
        this.calendarPlanningService = calendarPlanningService;
        this.classJournalRepository = classJournalRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/journal/{id}")
    public String getJournalPage(@PathVariable long id, Model model, Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());

        Teacher teacher = classJournalRepository.findById(id).get().getSchedule().getTeacher();

        if (teacher == teacherRepository.findByUserPersonalData(userPersonalData)){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }

        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }

        List<AdditionalJournalColumn> additionColumns = classJournalService.getAdditionColumns(id);
        List<String> firstSem = classJournalService.getAllDatesByDays(id, "I семестр", userPersonalData.getSchool());
        List<String> secondSem = classJournalService.getAllDatesByDays(id, "II семестр", userPersonalData.getSchool());



        String semester = calendarPlanningService.findNumberOfSemester(LocalDate.now(), firstSem, secondSem, false);

        if (semester.equals("II семестр")){
            model.addAttribute("isFirstSem", false);
            classJournalService.insertColumnToList(secondSem, additionColumns);
            model.addAttribute("days", secondSem);
            model.addAttribute("startDate", secondSem.get(0));
            model.addAttribute("finishDate", secondSem.get(secondSem.size()-1));
        }
        else {
            model.addAttribute("isFirstSem", true);
            classJournalService.insertColumnToList(firstSem, additionColumns);
            model.addAttribute("days", firstSem);
            model.addAttribute("startDate", firstSem.get(0));
            model.addAttribute("finishDate", firstSem.get(firstSem.size()-1));
        }


        List <String> listOfStudents = classJournalService.getStudents(id).stream().toList();

        String teacherName = classJournalService.getTeacherName(id);
        String className = classJournalService.getClassName(id);
        int classGroup = classJournalService.getClassGroup(id);
        String subject = classJournalService.getSubject(id);


        model.addAttribute("students", listOfStudents);
        model.addAttribute("teacherName", teacherName);
        model.addAttribute("className", className);
        model.addAttribute("classGroup", classGroup);
        model.addAttribute("subject", subject);
        model.addAttribute("journalId", id);

        return "Journal";
    }

    @PostMapping("/addcolumn")
    public ResponseEntity<?> addColumn(@RequestParam("jsonData") String jsonData){
        try {
            classJournalService.addNewColumnToJournal(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findcolumn")
    public ResponseEntity<?> getColumnData(@RequestParam("columnName") String columnName,
                                           @RequestParam("date") String date,
                                           @RequestParam("classJournal") String classJournal) {
        AdditionalJournalColumn additionalJournalColumn;
        try {
            additionalJournalColumn = classJournalService.getAllColumnData(date, classJournal, columnName);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(additionalJournalColumn, HttpStatus.OK);
    }

    @PostMapping("/editcolumn")
    public ResponseEntity <?> editColumn(@RequestParam("jsonData") String jsonData){
        try {
            classJournalService.editColumn(jsonData);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/date")
    public ResponseEntity<?> getDates(@RequestParam("journalId") long id,
                                      @RequestParam("semester") String semester,
                                      Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<AdditionalJournalColumn> additionColumns = classJournalService.getAdditionColumns(id);
        List<String> allDays = classJournalService.getAllDatesByDays(id, semester, userPersonalData.getSchool());  // 1
        classJournalService.insertColumnToList(allDays, additionColumns);
        return new ResponseEntity<>(allDays, HttpStatus.OK);
    }

    @GetMapping("/studentlist")
    public ResponseEntity<?> getStudents(@RequestParam("journalId") long id){
        List <String> listOfStudents = classJournalService.getStudents(id);  // 2
        return new ResponseEntity<>(listOfStudents, HttpStatus.OK);
    }
}
