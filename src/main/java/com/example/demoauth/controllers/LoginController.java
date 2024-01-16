package com.example.demoauth.controllers;

import com.example.demoauth.models.UserAuthData;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.IdRequest;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private UserService userService;




    public LoginController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/loginform")
    public String getLoginForm() {
        return "LoginRegisterForma";
    }

    @GetMapping("/myprofile")
    public String getAll() {
        return "MyProfile";
    }

    @GetMapping("/verify")
    public String verifyUserEmail(@RequestParam("code") String code, Model model) {
        boolean verified = userService.verify(code);
        if (verified) {
            model.addAttribute("verifySuccess", true);
        } else {
            model.addAttribute("verifySuccess", false);
        }
        return "SuccessfulVerifyAccount";
    }


}