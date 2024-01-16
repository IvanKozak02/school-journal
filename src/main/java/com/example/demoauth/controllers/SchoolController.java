package com.example.demoauth.controllers;


import com.example.demoauth.models.*;
import com.example.demoauth.pojo.MessageResponse;
import com.example.demoauth.pojo.SchoolData;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.service.AdministrationService;
import com.example.demoauth.service.SchoolService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SchoolController {
    private UserDataService userDataService;

    private RoleRepository roleRepository;
    private UserService userService;
    private SchoolService schoolService;

    private AdministrationService administrationService;




    public SchoolController(UserDataService userDataService, RoleRepository roleRepository, UserService userService, SchoolService schoolService, AdministrationService administrationService) {
        this.userDataService = userDataService;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.schoolService = schoolService;
        this.administrationService = administrationService;
    }

    @PostMapping("/school")
    public ResponseEntity<?> createNewSchool(@RequestBody SchoolData schoolData, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        UserAuthData userAuthData = userService.getUserByEmail(userPersonalData.getEmail());

        if (schoolService.checkIfExistsSchool(schoolData.getName())){
            return new ResponseEntity<>(new MessageResponse("Навчальний заклад вже підключено!!!"), HttpStatus.BAD_REQUEST);
        }
        else {
            Role role = roleRepository.findByName(ERole.ROLE_ADMIN).get();
            userAuthData.setRoles(Collections.singleton(role));
            schoolService.saveNewSchool(schoolData, userPersonalData);
            administrationService.createNewAdministrator(userPersonalData);
            return new ResponseEntity<>(new MessageResponse("Навчальний заклад успішно підключено!!!"), HttpStatus.OK);
        }
    }

    @GetMapping("/school/{id}")
    public ResponseEntity<?> getSchool(@PathVariable (name = "id")String schoolId){
        long id = Long.parseLong(schoolId);
        School school = schoolService.getSchool(id);
        return new ResponseEntity<>(school,HttpStatus.OK);
    }

    @GetMapping("/schools")
    public ResponseEntity<?> getAllSchools(){
        Set<School> schools = schoolService.getAll();

        if (schools.size()>0){
            return new ResponseEntity<>(schools,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Шкіл не знайдено"), HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/school")

    public String getSchoolPage(Model model, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        Set<Role> roleSet = userPersonalData.getUserAuthData().getRoles();
        List<Role> roleList = new ArrayList<>(roleSet);

        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }


        School school = userPersonalData.getSchool();
        model.addAttribute("schoolName",school.getName());
        model.addAttribute("typeOfSchool", school.getTypeOfSchool());
        model.addAttribute("webCite", school.getWebCite());
        model.addAttribute("region", school.getOblast());
        model.addAttribute("city", school.getCity().split(",")[0]);
        model.addAttribute("street", school.getStreet());
        model.addAttribute("email", school.getEmail());
        model.addAttribute("phone", school.getContactPhone());
        model.addAttribute("administration", administrationService.getAdministrationOfSchool(userPersonalData.getSchool()));

        List<String> roles = roleList.stream().map(e -> e.getName().name()).toList();
        model.addAttribute("roles", roles.toString());
        return "SchoolProfile";
    }

}
