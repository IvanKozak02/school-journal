package com.example.demoauth.controllers;

import com.example.demoauth.models.Application;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.ApplicationData;
import com.example.demoauth.pojo.ApplicationGetData;
import com.example.demoauth.pojo.ApproveApplication;
import com.example.demoauth.pojo.MessageResponse;
import com.example.demoauth.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
@Api(description = "Контролер, який містить бізнес-логіку обробки заявок")
public class ApplicationController {
    @Value("${upload.document}")
    String folderDoc;
    private ApplicationService applicationService;

    private UserDataService userDataService;

    private MessageService messageService;

    public ApplicationController(ApplicationService applicationService, UserDataService userDataService, MessageService messageService) {
        this.applicationService = applicationService;
        this.userDataService = userDataService;
        this.messageService = messageService;
    }

    @PostMapping("/newApplications")
    @ApiOperation("Створення нової заявки")
    public @ResponseBody ResponseEntity<?> createNewApplication(@RequestParam("jsonData") String jsonData,
                                                                @RequestParam ("file") MultipartFile multipartFile,
                                                                HttpServletRequest request) {
        try {
            applicationService.createNewApplication(jsonData,multipartFile,request);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new MessageResponse("Заявку успішно подано!!!"),HttpStatus.OK);
        
    }

    @GetMapping("/applications")
    @ApiOperation("Отримання всіх заявок")
    public String getAllApplication(Model model, Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userPrincipal.getEmail());
       List<Application> applications =  applicationService.getAllAppBySchool(userPersonalData.getSchool());

       if (applications.size()>0){
           model.addAttribute("isApplication", true);
           model.addAttribute("applications", applications);
       }
       else {
           model.addAttribute("isApplication", false);
       }
       return "Applications";
    }


    @PostMapping ("/application")
    @ApiOperation("Отримання конкретної заявки")
    public @ResponseBody ResponseEntity<?> getApplication(@RequestParam("jsonData") String jsonData) throws JsonProcessingException {
        Application application = applicationService.getApplication(jsonData);
        if (application != null){
            return new ResponseEntity<>(application,HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Заявки не знайдено!!!"),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("downloadFile/{fileName}")
    @ApiOperation("Завантаження фото копії документа, що посвідчує особу")
    public ResponseEntity<Resource> show(@PathVariable String fileName) throws IOException {
        File folder = new File(folderDoc);
        FileFilter logFileFilter = (file) -> file.getName().equals(fileName);
        File [] files = folder.listFiles(logFileFilter);
        if (files != null){
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Objects.requireNonNull(Files.probeContentType(files[0].toPath()))))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\"" + fileName + "\"")
                    .body(new ByteArrayResource(Files.readAllBytes(files[0].toPath())));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/application")
    @ApiOperation("Видалення заявки")
    public ResponseEntity<?> deleteApplication(@RequestParam("jsonData") String jsonData, HttpServletRequest request) throws JsonProcessingException {
        boolean isDeleted = applicationService.deleteApplication(jsonData, request);
        if (isDeleted){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/approveapplication")
    @ApiOperation("Схвалення заявки")
    public ResponseEntity<?> approveApplication(@RequestParam("jsonData") String jsonData) throws JsonProcessingException {
        if (applicationService.approveApplication(jsonData)){
            return new ResponseEntity<>(new ApproveApplication("Заявку успішно схвалено!!!",LocalDateTime.now()),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Трапилась помилка!!!"),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/rejectapplication")
    @ApiOperation("Відхилення заявки")
    public ResponseEntity<?> rejectApplication(@RequestParam("jsonData") String jsonData) {
        try {
            applicationService.rejectApplication(jsonData);
            return new  ResponseEntity<>(new MessageResponse("Заявку успішно відхилено!!!"),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Трапилась помилка!!!"),HttpStatus.BAD_REQUEST);
        }
    }

}
