package com.example.demoauth.controllers;

import com.example.demoauth.models.Certificates;
import com.example.demoauth.service.CertificateService;
import com.example.demoauth.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FileImageUploadController {

    private FileStorageService fileStorageService;

    private CertificateService certificateService;

    @Value("${upload.path}")
    private String uploadPath;

    public FileImageUploadController(FileStorageService fileStorageService, CertificateService certificateService) {
        this.fileStorageService = fileStorageService;
        this.certificateService = certificateService;
    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadCertificate(@RequestParam(name = "file") MultipartFile [] files, HttpServletRequest request){
        Arrays.stream(files).forEach(file -> {
            try {
             String resultPath=fileStorageService.uploadFiles(file,uploadPath);
             certificateService.createNewCertificate(request,resultPath);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        List<Certificates> certificates = certificateService.getAllCertificates(request);
        if (certificates.size() != 0){
            return new ResponseEntity<>(certificates, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}


