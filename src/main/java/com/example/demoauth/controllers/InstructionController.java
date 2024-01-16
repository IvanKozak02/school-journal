package com.example.demoauth.controllers;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.repository.ClassRepository;
import com.example.demoauth.repository.HeadTeacherRepository;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.TeacherRepository;
import com.example.demoauth.service.InstructionService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class InstructionController {

    private InstructionService instructionService;
    private ClassRepository classRepository;
    private UserDataService userDataService;

    private HeadTeacherRepository headTeacherRepository;

    private TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;

    public InstructionController(InstructionService instructionService, ClassRepository classRepository, UserDataService userDataService, HeadTeacherRepository headTeacherRepository, TeacherRepository teacherRepository,
                                 RoleRepository roleRepository) {
        this.instructionService = instructionService;
        this.classRepository = classRepository;
        this.userDataService = userDataService;
        this.headTeacherRepository = headTeacherRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/instructions/{classId}")
    public String getInstructionPage(@PathVariable long classId, Model model, Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());

        HeadTeacher headTeacher = headTeacherRepository.findByTeacher(teacherRepository.findByUserPersonalData(userPersonalData));

        if (headTeacher != null && headTeacher.getSchoolClass().getId() == classId){
            model.addAttribute("isHeadTeacher", true);
        }
        else {
            model.addAttribute("isHeadTeacher", false);
        }

        Set<Role> roleSet = new HashSet<>(userPersonalData.getUserAuthData().getRoles());
        if (roleSet.contains(roleRepository.findByName(ERole.ROLE_ADMIN).get())){
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }

        List<Instruction> instructionList = instructionService.getAllInstruction(classId);
        Class schoolClass = classRepository.findById(classId).get();
        model.addAttribute("class", schoolClass);
        if (instructionList.size() == 0){
            model.addAttribute("instructionEmpty", true);
        }
        else {
            model.addAttribute("instructionEmpty", false);
            model.addAttribute("instructions", instructionList);
        }
        return "Instruction";
    }

    @PostMapping("/instruction")
    public ResponseEntity<?> saveInstruction(@RequestParam("jsonData") String jsonData){
        try {
            instructionService.saveInstruction(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/instruction")
    public ResponseEntity<?> saveInstruction(@RequestParam("instructionId") long id){
        try {
            instructionService.deleteInstruction(id);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
