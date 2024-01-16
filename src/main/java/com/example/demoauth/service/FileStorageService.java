package com.example.demoauth.service;

import com.example.demoauth.models.Certificates;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.CertificateRepos;
import com.example.demoauth.repository.UserPersonalDataRepos;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    @Value("${upload.path}")
    private String uploadPath;


    private UserPersonalDataRepos userPersonalDataRepos;

    private CertificateRepos certificateRepos;

    public FileStorageService(UserPersonalDataRepos userPersonalDataRepos, CertificateRepos certificateRepos) {
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.certificateRepos = certificateRepos;
    }

    public String uploadFiles(MultipartFile path, String uploadPath) throws IOException {   // завантаження зображення на сервер
        String resultCertificatePath = null;
        if (path != null){
            File uploadFolder = new File(uploadPath);
            if (!uploadFolder.exists()){
                uploadFolder.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            resultCertificatePath = uuid + "." + path.getOriginalFilename();
            path.transferTo(new File(uploadPath + "/" + resultCertificatePath));
        }
//        if (Objects.equals(typeOfDoc, "Сертифікат")){
////            Cookie [] cookies = request.getCookies();
////            String id = Arrays.stream(cookies).filter((cookie)->cookie.getName().equals("userId")).collect(Collectors.toList()).get(0).getValue();
////            UserPersonalData userPersonalData = userPersonalDataRepos.findUserPersonalDataById(id);
////            Certificates certificates = new Certificates(resultCertificatePath,userPersonalData);
////            certificateRepos.save(certificates);
//        }
//        else {
//
//        }
        return resultCertificatePath;

    }

//    public List<Certificates> getAllCertificates(HttpServletRequest request){
//        Cookie [] cookies = request.getCookies();
//        String id = Arrays.stream(cookies).filter((cookie)->cookie.getName().equals("userId")).collect(Collectors.toList()).get(0).getValue();
//        return certificateRepos.findAllByUserPersonalDataId(id);
//    }
//
//    public void deleteCert(String id){
//        certificateRepos.deleteById(Long.parseLong(id));
//
//    }
}
