package com.example.demoauth.controllers;

import com.example.demoauth.models.AttendanceRecord;
import com.example.demoauth.models.ERole;
import com.example.demoauth.models.Role;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.AttendanceRecordRepository;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.service.AttendanceRecordService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import com.example.demoauth.service.VisitStatisticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class VisitStatisticController {

    private VisitStatisticService visitStatisticService;

    private UserDataService userDataService;

    private AttendanceRecordRepository attendanceRecordRepository;
    private final RoleRepository roleRepository;

    public VisitStatisticController(VisitStatisticService visitStatisticService, UserDataService userDataService, AttendanceRecordRepository attendanceRecordRepository,
                                    RoleRepository roleRepository) {
        this.visitStatisticService = visitStatisticService;
        this.userDataService = userDataService;
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/visit-statistics")
    public String getStatisticPage(@RequestParam("journalId") long id, @RequestParam("time") long time, Authentication authentication,
                                   Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<String> countOfN = visitStatisticService.getCountOfNForStudents(time, userPersonalData, id);
        AttendanceRecord journal = attendanceRecordRepository.findById(id).get();
        model.addAttribute("journalId", id);
        model.addAttribute("class", journal.getSchoolClass().getNameOfClass());
        model.addAttribute("teacherName", journal.getTeacher().getUserPersonalData().getUserName());
        model.addAttribute("studentList", countOfN);
        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }
        return "OblikStatystyka";
    }

    @GetMapping("/count-of-N")
    public ResponseEntity<?> getAllN(@RequestParam("journalId") long id, @RequestParam("time") long time, Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<String> countOfN = visitStatisticService.getCountOfNForStudents(time, userPersonalData, id);
        return new ResponseEntity<>(countOfN, HttpStatus.OK);
    }
}
