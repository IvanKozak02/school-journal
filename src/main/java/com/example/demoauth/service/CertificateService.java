package com.example.demoauth.service;

import com.example.demoauth.models.Certificates;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.CertificateRepos;
import com.example.demoauth.repository.UserPersonalDataRepos;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    private final CertificateRepos certificateRepos;

    private UserPersonalDataRepos userPersonalDataRepos;

    public CertificateService(CertificateRepos certificateRepos, UserPersonalDataRepos userPersonalDataRepos) {
        this.certificateRepos = certificateRepos;
        this.userPersonalDataRepos = userPersonalDataRepos;
    }

    public List<Certificates> getAllCertificates(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie)->cookie.getName().equals("userId")).collect(Collectors.toList()).get(0).getValue();
        return certificateRepos.findAllByUserPersonalDataId(id);
    }

    public void deleteCert(String id){
        certificateRepos.deleteById(Long.parseLong(id));
    }

    public void createNewCertificate(HttpServletRequest request,String resultCertificatePath){
        Cookie [] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie)->cookie.getName().equals("userId")).collect(Collectors.toList()).get(0).getValue();
        UserPersonalData userPersonalData = userPersonalDataRepos.findUserPersonalDataById(id);
        Certificates certificates = new Certificates(resultCertificatePath,userPersonalData);
        certificateRepos.save(certificates);
    }
}
