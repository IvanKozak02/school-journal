package com.example.demoauth.controllers;

import com.example.demoauth.models.Certificates;
import com.example.demoauth.service.CertificateService;
import com.example.demoauth.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CertificateController {

    private FileStorageService fileStorageService;

    private CertificateService certificateService;

    public CertificateController(FileStorageService fileStorageService, CertificateService certificateService) {
        this.fileStorageService = fileStorageService;
        this.certificateService = certificateService;
    }

    @DeleteMapping("/deletecert")
    public ResponseEntity<?> deleteCert(@RequestParam("certId") String id){
        certificateService.deleteCert(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/allcertificate")
    public ResponseEntity<?> getAllCert(HttpServletRequest request){
        List<Certificates> certificates = certificateService.getAllCertificates(request);
        if (certificates.size() != 0){
            return new ResponseEntity<>(certificates, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
