package com.example.demoauth.controllers;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.ClassJournal;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.ClassJournalRepository;
import com.example.demoauth.repository.ClassRepository;
import com.example.demoauth.service.AdminCabinetService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import org.dom4j.rule.Mode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminCabinetController {

    private UserDataService userDataService;

    private ClassRepository classRepository;

    private AdminCabinetService adminCabinetService;

    private ClassJournalRepository classJournalRepository;

    public AdminCabinetController(UserDataService userDataService, ClassRepository classRepository, AdminCabinetService adminCabinetService, ClassJournalRepository classJournalRepository) {
        this.userDataService = userDataService;
        this.classRepository = classRepository;
        this.adminCabinetService = adminCabinetService;
        this.classJournalRepository = classJournalRepository;
    }

    @GetMapping("/admin-cabinet")
    public String getAdminCabinetPage(){
        return "CabinetAdmin";
    }

    @GetMapping("/admin-cabinet-oblik")
    public String getOblikPage(Authentication authentication, Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<Class> classes = classRepository.findBySchool(userPersonalData.getSchool());
        List <String> classesAndId = adminCabinetService.findAttendanceRecJournal(classes);
        model.addAttribute("classes", classesAndId);
        return "AdminCabinetOblik";
    }

    @GetMapping("/admin-cabinet-instruction")
    public String getInstructionPage(Authentication authentication, Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<Class> classes = classRepository.findBySchool(userPersonalData.getSchool());
        model.addAttribute("classes", classes);
        return "AdminCabinetInstruction";
    }

    @GetMapping("/admin-cabinet-motion-table")
    public String getMotionPage(Authentication authentication, Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<Class> classes = classRepository.findBySchool(userPersonalData.getSchool());
        model.addAttribute("classes", classes);
        return "AdminCabinetMotion";
    }

    @GetMapping("/admin-cabinet-journal")
    public String getJournalPage(Authentication authentication, Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<Class> classes = classRepository.findBySchool(userPersonalData.getSchool());
        model.addAttribute("classes", classes);
        return "AdminCabinetJournal";
    }

    @GetMapping("/admin-cabinet-subjects/{classId}")
    public ResponseEntity<?> getSubjectsByClass(@PathVariable long classId){
        List<ClassJournal> classJournals = classJournalRepository.findBySchedule_SchoolClass_Id(classId);
        Set<String> subjects = classJournals.stream().map(j -> j.getSchedule().getSubject().getName()).collect(Collectors.toSet());
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/admin-cabinet-subject-journal")
    public ResponseEntity<?> getGroupByClassAndSubjectName(@RequestParam("classId") long classId,
                                                           @RequestParam("subject") String subject){
        List<ClassJournal> classJournals = classJournalRepository.findBySchedule_SchoolClass_IdAndSchedule_Subject_Name(classId,subject);
        return new ResponseEntity<>(classJournals.stream().sorted((Comparator.comparingInt(o -> o.getSchedule().getClassGroup().getNumberOfGroup()))), HttpStatus.OK);
    }

    @GetMapping("/admin-cabinet-subject-journal-group")
    public ResponseEntity<?> getGroupByClassAndSubjectName(@RequestParam("classId") long classId,
                                                           @RequestParam("subject") String subject,
                                                           @RequestParam("group") int group){
        List<ClassJournal> classJournal = classJournalRepository.findBySchedule_SchoolClass_IdAndSchedule_Subject_NameAndSchedule_ClassGroup_NumberOfGroup(classId,subject,group);
        return new ResponseEntity<>(classJournal, HttpStatus.OK);
    }
}
