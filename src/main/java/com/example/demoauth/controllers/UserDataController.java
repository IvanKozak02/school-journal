package com.example.demoauth.controllers;

import com.example.demoauth.models.ERole;
import com.example.demoauth.models.Role;
import com.example.demoauth.models.UserAuthData;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.IdRequest;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.UserRepository;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import com.example.demoauth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserDataController {

    private UserDataService userDataService;

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private UserService userService;

    public UserDataController(UserDataService userDataService, UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userDataService = userDataService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostMapping("/addNewUserProfile")
    public ResponseEntity<?> addNewUserProfile(@RequestBody UserPersonalData userPersonalData, Authentication authentication, ModelMap model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        userPersonalData.setEmail(userPrincipal.getEmail());
        UserAuthData userAuthData = userRepository.findByEmail(userPersonalData.getEmail()).get();
        userAuthData.setHasProfile(true);
        if (Objects.equals(userPersonalData.getRole(), "Працівник закладу освіти")){
            Role role = roleRepository.findByName(ERole.ROLE_EMPLOYEE).get();
            userAuthData.setRoles(Collections.singleton(role));
        }
        else {
            Role role = roleRepository.findByName(ERole.ROLE_STUDENT).get();
            userAuthData.setRoles(Collections.singleton(role));
        }
        userPersonalData.setUserAuthData(userAuthData);
        userDataService.saveNewProfile(userPersonalData);

        return new ResponseEntity<>(new IdRequest(userService.getUserProfileId(userPersonalData.getEmail())),HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestParam("id") String id){
        UserPersonalData userPersonalData = userDataService.getUserProfile(id);
        if (userPersonalData != null){
            return new ResponseEntity<>(userPersonalData, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
