package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.repository.AttendanceRecordRepository;
import com.example.demoauth.repository.HeadTeacherRepository;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.TeacherRepository;
import com.example.demoauth.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AttendanceRecordController {

    private AttendanceRecordService attendanceRecordService;
    private UserDataService userDataService;

    private AttendanceRecordRepository attendanceRecordRepository;

    private CalendarPlanningService calendarPlanningService;
    private final HeadTeacherRepository headTeacherRepository;
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;


    public AttendanceRecordController(AttendanceRecordService attendanceRecordService, UserDataService userDataService, AttendanceRecordRepository attendanceRecordRepository, CalendarPlanningService calendarPlanningService,
                                      HeadTeacherRepository headTeacherRepository,
                                      TeacherRepository teacherRepository,
                                      RoleRepository roleRepository) {
        this.attendanceRecordService = attendanceRecordService;
        this.userDataService = userDataService;
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.calendarPlanningService = calendarPlanningService;
        this.headTeacherRepository = headTeacherRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("attendance-record/{id}")
    public String getAttendanceRecordPage(@PathVariable long id, Authentication authentication, Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }
        List<String> firstSem = attendanceRecordService.findAllDates("I семестр",userPersonalData.getSchool());
        List<String> secondSem = attendanceRecordService.findAllDates("II семестр",userPersonalData.getSchool());

        String semester = calendarPlanningService.findNumberOfSemester(LocalDate.now(), firstSem, secondSem, true);

        if (semester.equals("II семестр")){
            model.addAttribute("isFirstSem", false);
            model.addAttribute("days", secondSem);
        }
        else {
            model.addAttribute("isFirstSem", true);
            model.addAttribute("days", firstSem);
        }

        List<String> students = attendanceRecordService.getStudents(id);
        String className = attendanceRecordService.getClassName(id);
        String teacherName = attendanceRecordService.getTeacherName(id);

        HeadTeacher headTeacher = headTeacherRepository.findBySchoolClass(attendanceRecordRepository.findById(id).get().getSchoolClass());
        if (headTeacher.getTeacher() == teacherRepository.findByUserPersonalData(userPersonalData)){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }



        model.addAttribute("journalId", id);
        model.addAttribute("students", students);
        model.addAttribute("className", className);
        model.addAttribute("teacherName", teacherName);
        model.addAttribute("classId", attendanceRecordRepository.findById(id).get().getSchoolClass().getId());

        return "AttendanceRecord";
    }


    @GetMapping("/attendance-rec")
    public ResponseEntity<?> getAllSubjects(@RequestParam("date")String date,
                                            @RequestParam("studentId") String studentId,
                                            @RequestParam("classId") long classId,
                                            @RequestParam("index") long index) {
        List<String> subjects;
        try {
            subjects = attendanceRecordService.getAllSubjects(date, studentId, classId, (int) index);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/attendance-rec-mark")
    public ResponseEntity<?> getAllN(@RequestParam("date")String date,
                                            @RequestParam("studentId") String studentId) {
        List<Mark> N;
        try {
            N = attendanceRecordService.getAllN(date, studentId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(N, HttpStatus.OK);
    }
    @GetMapping("/attendance-rec-n")
    public ResponseEntity<?> getAllNByStudents(@RequestParam("studentId") String studentId) {
        List<Mark> N;
        try {
            N = attendanceRecordService.getAllNByClass(studentId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(N, HttpStatus.OK);
    }

    @GetMapping("/attendance-rec-date")
    public ResponseEntity<?> getAllDates(@RequestParam("semester") String semester, Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<String> dates;
        try {
            dates = attendanceRecordService.findAllDates(semester, userPersonalData.getSchool());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dates, HttpStatus.OK);
    }
    @GetMapping("/attendance-rec-students")
    public ResponseEntity<?> getAllDates(@RequestParam("journalId") long id) {
        List<String> students;
        try {
            students = attendanceRecordService.getStudents(id);;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/change-type-n")
    public ResponseEntity<?> changeTypeOfN(@RequestParam("jsonData") String jsonData){
        try {
            attendanceRecordService.changeN(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
