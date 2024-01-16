package com.example.demoauth.controllers;

import com.example.demoauth.models.ERole;
import com.example.demoauth.models.Role;
import com.example.demoauth.models.StructureOfYear;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.Semester;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.service.StructureService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SettingsController {

    private StructureService structureService;

    private UserDataService userDataService;
    private final RoleRepository roleRepository;

    public SettingsController(StructureService structureService, UserDataService userDataService,
                              RoleRepository roleRepository) {
        this.structureService = structureService;
        this.userDataService = userDataService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/settings")
    public String getSettingsPage(Authentication authentication, Model model){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());

        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("hasAccess", true);
        }
        else {
            model.addAttribute("hasAccess", false);
        }

        return "Settings";
    }

    @PostMapping("/year-structure")
    public ResponseEntity<?> saveYearStructure(@RequestParam ("firstSem") String firstSem,
                                               @RequestParam ("secondSem") String secondSem, HttpServletRequest request){
        try{
            structureService.saveStructure(firstSem, secondSem,request);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/structure")
    public ResponseEntity<?> getStructure(){
        List<StructureOfYear> structure = structureService.getStructure();
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

}
