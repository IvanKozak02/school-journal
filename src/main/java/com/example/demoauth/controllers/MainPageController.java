package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.pojo.ProfileUpdate;
import com.example.demoauth.repository.*;
import com.example.demoauth.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainPageController {

    private UserDataService userDataService;


    private UserPersonalDataRepos userPersonalDataRepos;

    private UserService userService;

    private CertificateService certificateService;

    private RoleRepository roleRepository;

    private TeacherService teacherService;

    private ClassJournalService classJournalService;

    private HeadTeacherRepository headTeacherRepository;

    private AttendanceRecordRepository attendanceRecordRepository;
    private final TeacherRepository teacherRepository;
    private final AdministrationRepos administrationRepos;


    public MainPageController(UserDataService userDataService, UserPersonalDataRepos userPersonalDataRepos, UserService userService, CertificateService certificateService, RoleRepository roleRepository, TeacherService teacherService, ClassJournalService classJournalService, HeadTeacherRepository headTeacherRepository, AttendanceRecordRepository attendanceRecordRepository, TeacherRepository teacherRepository,
                              AdministrationRepos administrationRepos) {
        this.userDataService = userDataService;
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.userService = userService;
        this.certificateService = certificateService;
        this.roleRepository = roleRepository;
        this.teacherService = teacherService;
        this.classJournalService = classJournalService;
        this.headTeacherRepository = headTeacherRepository;
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.teacherRepository = teacherRepository;
        this.administrationRepos = administrationRepos;
    }

    @GetMapping("/mainpage")
    public String getMainPage(HttpServletRequest request, Model model, Authentication authentication){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie)->cookie.getName().equals("userId")).collect(Collectors.toList()).get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        UserAuthData userAuthData = userService.getUserByEmail(userPersonalData.getEmail());
        Set<Role> roleSet = new HashSet<>(userAuthData.getRoles());
        Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        Role role_teacher = roleRepository.findByName(ERole.ROLE_TEACHER).get();
        Role role_employee = roleRepository.findByName(ERole.ROLE_EMPLOYEE).get();
        Role role_student = roleRepository.findByName(ERole.ROLE_STUDENT).get();
        if (roleSet.contains(role_admin)){
            return "redirect:/admin";
        } else if (roleSet.contains(role_student)) {
            return "redirect:/student";
        }
        if (roleSet.contains(role_teacher) || roleSet.contains(role_employee)){
            if (userPersonalData.getPosition() == null){
                model.addAttribute("position", "");
            }
            else {
                model.addAttribute("position", userPersonalData.getPosition());
            }

            if (userPersonalData.getSchool() != null){
                model.addAttribute("hasSchool", true);
            }
            else{
                model.addAttribute("hasSchool", false);
            }

            if (teacherService.existsByUserData(userPersonalData)){
                model.addAttribute("hasRoleTeacher", true);
            }
            else {
                model.addAttribute("hasRoleTeacher", false);
            }

            if (userPersonalData.getUserAuthData().getRoles().contains(role_teacher)){
                model.addAttribute("schoolName", userPersonalData.getSchool().getName());
                Set<Subject> subjectSet = teacherService.getTeacherByUserPersonalData(userPersonalData).getSubjects();
                model.addAttribute("subjects", transformAllSubjectToString(subjectSet));
            }

            List<Certificates> certificates = certificateService.getAllCertificates(request);
            if (certificates.size()>0){
                model.addAttribute("isCertificates",true);
                model.addAttribute("certificates", certificates);
            }
            else {
                model.addAttribute("isCertificates",false);
            }
            model.addAttribute("teacherName",userPersonalData.getUserName());
            model.addAttribute("telNumber", userPersonalData.getWorkPhone());
            model.addAttribute("id", userPersonalData.getId());
            model.addAttribute("email", userPersonalData.getEmail());
            model.addAttribute("dateOfBirth", userPersonalData.getDateOfBirth());
            model.addAttribute("isStudent", false);
        }



        /*============================STUDENT==========================================*/
        else {
            model.addAttribute("position", "Учень");
            model.addAttribute("isCertificates",false);
            model.addAttribute("hasSchool", false);
            model.addAttribute("isStudent", true);
            model.addAttribute("hasRoleTeacher", false);
            model.addAttribute("teacherName",userPersonalData.getUserName());
            model.addAttribute("telNumber", userPersonalData.getWorkPhone());
            model.addAttribute("id", userPersonalData.getId());
            model.addAttribute("email", userPersonalData.getEmail());
            model.addAttribute("dateOfBirth", userPersonalData.getDateOfBirth());
        }

        /*+++++++++++++++++++ЖУРНАЛИ++++++++++++++++++++++++*/
        Teacher teacher = null;
        if (teacherService.existsByUserData(userPersonalData)){
            List<ClassJournal> classJournals = classJournalService.getAllJournalsForTeacher(teacherService.getTeacherByUserPersonalData(userPersonalData));
            model.addAttribute("journals", classJournals);
            teacher = teacherRepository.findByUserPersonalData(userPersonalData);
            model.addAttribute("hasClassJournal", !classJournals.isEmpty());

        }
        else {
            model.addAttribute("hasClassJournal", false);
        }
        /*+++++++++++++++++++ЖУРНАЛИ++++++++++++++++++++++++*/

        /*+++++++++++++++++++Класний керівник++++++++++++++++++++++++*/

        if (teacher != null){
            if (teacher.isClassTeacher()){
                AttendanceRecord attendanceRecord = attendanceRecordRepository.findByTeacher(teacher);
                model.addAttribute("isClassTeacher", true);
                if (attendanceRecord.getSchoolClass().getStudents().size() == 0){
                    model.addAttribute("hasStudent", true);
                    model.addAttribute("attendanceJournal", '#');
                }
                else {
                    model.addAttribute("hasStudent", true);
                    model.addAttribute("attendanceJournal", attendanceRecord.getId());


                }
                model.addAttribute("class", headTeacherRepository.findByTeacher(teacher).getSchoolClass().getNameOfClass());
            }
        }
        else {
            model.addAttribute("isClassTeacher", false);
        }
        /*+++++++++++++++++++Класний керівник++++++++++++++++++++++++*/

        return "MainPage(v1)";
    }

    @GetMapping("/admin")
    public String getAdminPage(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttrs){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        model.addAttribute("hasSchool", true);
        if (userPersonalData.getUserAuthData().getRoles().contains(role_admin)){
            if (administrationRepos.findByUserPersonalData(userPersonalData).getPosition() != null){
                model.addAttribute("position", administrationRepos.findByUserPersonalData(userPersonalData).getPosition());
            }
            else if (userPersonalData.getPosition() != null){
                model.addAttribute("position", userPersonalData.getPosition());
            }
            else {
                model.addAttribute("position", "");
            }
            List<Certificates> certificates = certificateService.getAllCertificates(request);
            if (certificates.size()>0){
                model.addAttribute("isCertificates",true);
                model.addAttribute("certificates", certificates);
            }
            else {
                model.addAttribute("isCertificates",false);
            }
            model.addAttribute("teacherName",userPersonalData.getUserName());
            model.addAttribute("telNumber", userPersonalData.getWorkPhone());
            model.addAttribute("id", userPersonalData.getId());
            model.addAttribute("email", userPersonalData.getEmail());
            model.addAttribute("dateOfBirth", userPersonalData.getDateOfBirth());
            model.addAttribute("isStudent", false);

            if (teacherService.existsByUserData(userPersonalData)){
                model.addAttribute("hasRoleTeacher", true);
                model.addAttribute("schoolName", userPersonalData.getSchool().getName());
                model.addAttribute("subjects", transformAllSubjectToString(teacherService.getTeacherByUserPersonalData(userPersonalData).getSubjects()));
            }
            else {
                model.addAttribute("hasRoleTeacher", false);
                model.addAttribute("subjects", userPersonalData.getSchool().getSubjects());
            }
            Teacher teacher = null;
            if (teacherService.existsByUserData(userPersonalData)){
                List<ClassJournal> classJournals = classJournalService.getAllJournalsForTeacher(teacherService.getTeacherByUserPersonalData(userPersonalData));
                model.addAttribute("journals", classJournals);
                model.addAttribute("hasClassJournal", !classJournals.isEmpty());
                teacher = teacherRepository.findByUserPersonalData(userPersonalData);
            }
            else {
                model.addAttribute("hasClassJournal", false);
            }
            if (teacher != null){
                if (teacher.isClassTeacher()){
                    AttendanceRecord attendanceRecord = attendanceRecordRepository.findByTeacher(teacher);
                    if (attendanceRecord != null){
                        model.addAttribute("isClassTeacher", true);
                        if (attendanceRecord.getSchoolClass().getStudents().size() == 0){
                            model.addAttribute("attendanceJournal", '#');
                            model.addAttribute("hasStudent", false);

                        }
                        else {
                            model.addAttribute("attendanceJournal", attendanceRecord.getId());
                            model.addAttribute("hasStudent", true);

                        }
                    }
                    else {
                        model.addAttribute("isClassTeacher", false);

                    }
                    model.addAttribute("class", headTeacherRepository.findByTeacher(teacher).getSchoolClass().getNameOfClass());
                }
                else {
                    model.addAttribute("isClassTeacher", false);
                }
            }
            else {
                model.addAttribute("isClassTeacher", false);
            }


            return "AdminPage";
        }
        redirectAttrs.addFlashAttribute("message", "Ви не є адміном!!!");
        return "redirect:/errors";
    }

    @GetMapping("/errors")
    public String getErrorPage(@ModelAttribute("message") String flashAttr,Model model){
        model.addAttribute("message", flashAttr);
        return "ErrorPage";
    }


    @PostMapping("/updateprofile")
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileUpdate profile, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        userPersonalData.setUserName(profile.getUsername());
        userPersonalData.setWorkPhone(profile.getPhoneNumber());
        userPersonalData.setEmail(profile.getEmail());
        userPersonalData.setPosition(profile.getPosition());
        userPersonalDataRepos.save(userPersonalData);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public String transformAllSubjectToString(Set<Subject> subjectSet){
        List<Subject> subjects = new ArrayList<>(subjectSet);
        String subjectsStr = "";

        for (int i = 0; i < subjectSet.size(); i++) {
            if (subjects.size()-i == 1){
                subjectsStr += subjects.get(i).getName() + ".";
                break;
            }
            subjectsStr += subjects.get(i).getName() + ", ";
        }
        return subjectsStr;

    }
}
