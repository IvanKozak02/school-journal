package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.pojo.ClassData;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.service.ClassProfileService;
import com.example.demoauth.service.ClassService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Api(description = "Контролер, який містить бізнес-логіку роботи з класом")
public class ClassController {

    private ClassService classService;

    private UserDataService userDataService;

    private RoleRepository roleRepository;


    public ClassController(ClassService classService, UserDataService userDataService, RoleRepository roleRepository) {
        this.classService = classService;
        this.userDataService = userDataService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/class")
    @ApiOperation("Метод, що відповідає за створення класу")
    public ResponseEntity<?> createNewClass(@RequestParam("jsonData") String jsonData) {
        try {
            classService.createNewClass(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/classes")
    @ApiOperation("Метод, що відповідає за відображення сторінки із списком класів")
    public String getClassListPage(Model model, Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        List<Class> classes = classService.getAllClasses(userPersonalData.getSchool());
        Set<Role> roles = userPersonalData.getUserAuthData().getRoles();
        Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();

        if (roles.contains(role_admin)){
            model.addAttribute("role", "YES");
        }
        else {
            model.addAttribute("role", "NO");
        }


        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }

        if (classes.isEmpty()){
            model.addAttribute("classNotFound", true);
        }
        else {
            model.addAttribute("classNotFound", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("1")))){
            model.addAttribute("isFirstClass", true);
            model.addAttribute("firstClasses", classes.stream().filter(c->c.getNameOfClass().contains("1")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isFirstClass", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("2")))){
            model.addAttribute("isSecondClass", true);
            model.addAttribute("secondClasses", classes.stream().filter(c->c.getNameOfClass().contains("2")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isSecondClass", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("3")))){
            model.addAttribute("isThirdClass", true);
            model.addAttribute("thirdClasses", classes.stream().filter(c->c.getNameOfClass().contains("3")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isThirdClass", false);
        }

        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("4")))){
            model.addAttribute("isfourthClass", true);
            model.addAttribute("fourthClasses", classes.stream().filter(c->c.getNameOfClass().contains("4")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isfourthClass", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("5")))){
            model.addAttribute("isfifthClass", true);
            model.addAttribute("fifthClasses", classes.stream().filter(c->c.getNameOfClass().contains("5")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isfifthClass", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("6")))){
            model.addAttribute("isSixthClass", true);
            model.addAttribute("sixthClasses", classes.stream().filter(c->c.getNameOfClass().contains("6")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isSixthClass", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("7")))){
            model.addAttribute("isSeventhClass", true);
            model.addAttribute("seventhClasses", classes.stream().filter(c->c.getNameOfClass().contains("7")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isSeventhClass", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("8")))){
            model.addAttribute("isEighthClass", true);
            model.addAttribute("eighthClasses", classes.stream().filter(c->c.getNameOfClass().contains("8")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isEighthClass", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("9")))){
            model.addAttribute("isNinthClass", true);
            model.addAttribute("ninthClasses", classes.stream().filter(c->c.getNameOfClass().contains("9")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isNinthClass", false);
        }

        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("10")))){
            model.addAttribute("isTenthlass", true);
            model.addAttribute("tenthClasses", classes.stream().filter(c->c.getNameOfClass().contains("10")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isTenthlass", false);
        }
        if ((classes.stream().anyMatch(c -> c.getNameOfClass().contains("11")))){
            model.addAttribute("isEleventhlass", true);
            model.addAttribute("eleventhClasses", classes.stream().filter(c->c.getNameOfClass().contains("11")).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("isEleventhlass", false);
        }
        return "ListOfClasses";
    }

    @DeleteMapping("/deleteclass")
    @ApiOperation("Метод, що відповідає за видалення класу")
    public ResponseEntity<?> deleteClass(@RequestParam("classId") String classId) {
        try {
            classService.deleteClassById(classId);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allclasses")
    @ApiOperation("Метод, що відповідає за отримання списку всіх класів")
    public ResponseEntity<?> getAllClasses(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
        School school = userPersonalData.getSchool();
        List<Class> classes = classService.getAllClasses(school);
        return new ResponseEntity<>(classes,HttpStatus.OK);
    }



}
