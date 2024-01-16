package com.example.demoauth.controllers;

import com.example.demoauth.models.Certificates;
import com.example.demoauth.models.Student;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.MessageResponse;
import com.example.demoauth.service.CertificateService;
import com.example.demoauth.service.StudentService;
import com.example.demoauth.service.UserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    private UserDataService userDataService;
    private CertificateService certificateService;

    private StudentService studentService;

    public StudentController(UserDataService userDataService, CertificateService certificateService, StudentService studentService) {
        this.userDataService = userDataService;
        this.certificateService = certificateService;
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public String getStudentPage(Model model, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        model.addAttribute("position", "Учень");
        List<Certificates> certificates = certificateService.getAllCertificates(request);
        if (certificates.size()>0){
            model.addAttribute("isCertificates",true);
            model.addAttribute("certificates", certificates);
        }
        else {
            model.addAttribute("isCertificates",false);
        }
        model.addAttribute("studentName",userPersonalData.getUserName());
        model.addAttribute("telNumber", userPersonalData.getWorkPhone());
        model.addAttribute("id", userPersonalData.getId());
        model.addAttribute("email", userPersonalData.getEmail());
        model.addAttribute("dateOfBirth", userPersonalData.getDateOfBirth());

        if (userPersonalData.getSchool() != null){
            model.addAttribute("hasSchool", true);
            model.addAttribute("schoolName", userPersonalData.getSchool().getName());
            Student student = studentService.getStudentByUserData(userPersonalData);
            model.addAttribute("parentsName", student.getParentsName());
            model.addAttribute("parentsPhone", student.getParentsPhones());
        }
        else{
            model.addAttribute("hasSchool", false);
//            model.addAttribute("schoolName", userPersonalData.getSchool().getName());

        }
        return "StudentPage";
    }

    @PostMapping ("/addstudenttoclass")
    public @ResponseBody ResponseEntity<?> addStudentToClass(@RequestParam("jsonData") String jsonData){
        try {
            studentService.addStudentToClass(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allstudentswithoutclass")
    public ResponseEntity<?> getAllStudentsWithoutClass(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        try {
            List<Student> studentsWithoutClass = studentService.getAllStudentsBySchool(userPersonalData.getSchool()).stream()
                    .filter(s -> s.getSchoolClass() == null).toList();
            return new ResponseEntity<>(studentsWithoutClass,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deletestudentfromclass")
    public ResponseEntity<?> deleteStudentFromClass(@RequestParam(name = "jsonData")String jsonData, HttpServletRequest request){
        try {
            Cookie[] cookies = request.getCookies();
            String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
            UserPersonalData userPersonalData = userDataService.getUserProfile(id);
            studentService.deleteStudentById(jsonData, userPersonalData);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Ви не можете видалити учня з даного класу, оскільки ви не є класним керівником!!!"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/allstudentsbyclass")
    public ResponseEntity<?> getAllStudentsByClass(@RequestParam("classId") String classId){
        List<Student> students = studentService.getAllStudentsByClass(classId);
        if (!students.isEmpty()){
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        return new ResponseEntity<>(students,HttpStatus.BAD_REQUEST);
    }

}
