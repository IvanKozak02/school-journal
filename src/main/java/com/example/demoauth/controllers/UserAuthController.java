package com.example.demoauth.controllers;

import com.example.demoauth.pojo.IdRequest;
import com.example.demoauth.pojo.LoginRequest;
import com.example.demoauth.pojo.MessageResponse;
import com.example.demoauth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {
    private final UserService userService;


    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/isEnabled")
    public ResponseEntity<?> checkIfUserIsEnabled(@RequestBody LoginRequest loginRequest) {
        if (userService.checkIfUserEnable(loginRequest.getEmail(), loginRequest.getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            if (!userService.checkIfUserExists(loginRequest.getEmail()) && !userService.checkIfUserEnable(loginRequest.getEmail(), loginRequest.getPassword())) {
                return new ResponseEntity<>(new MessageResponse("Неправильно введений логін або пароль"), HttpStatus.BAD_REQUEST);
            }
            else if (userService.checkIfUserExists(loginRequest.getEmail()) && !userService.checkIfUserPasswordIsOk(loginRequest.getEmail(),loginRequest.getPassword())){
                return new ResponseEntity<>(new MessageResponse("Неправильно введений логін або пароль"), HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<>(new MessageResponse("Активуйте ваш акаунт!!!"), HttpStatus.BAD_REQUEST);
            }
        }

    }

    @PostMapping("/hasProfile")
    public ResponseEntity<?> checkIfUserHasProfile(@RequestBody LoginRequest loginRequest) {
        if (userService.checkIfUserHasProfile(loginRequest.getEmail())) {
            return new ResponseEntity<>(new IdRequest(userService.getUserProfileId(loginRequest.getEmail())),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new IdRequest(userService.getUserProfileId(loginRequest.getEmail())),HttpStatus.BAD_REQUEST);
            }
    }
}
