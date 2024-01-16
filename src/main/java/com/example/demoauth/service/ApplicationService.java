package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.pojo.ApplicationData;
import com.example.demoauth.pojo.ApplicationGetData;
import com.example.demoauth.pojo.StudentApplication;
import com.example.demoauth.repository.ApplicationRepository;
import com.example.demoauth.repository.MessageRepository;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.UserPersonalDataRepos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private FileStorageService fileStorageService;

    @Value("${upload.document}")
    private String uploadDoc;

    private UserDataService userDataService;

    private SchoolService schoolService;

    private ApplicationRepository applicationRepository;

    private TeacherService teacherService;

    private RoleRepository roleRepository;

    private MessageRepository messageRepository;

    private MessageService messageService;

    private UserPersonalDataRepos userPersonalDataRepos;

    private AdministrationService administrationService;

    private StudentService studentService;
    ObjectMapper objectMapper = new ObjectMapper();


    public ApplicationService(FileStorageService fileStorageService, UserDataService userDataService, SchoolService schoolService, ApplicationRepository applicationRepository, TeacherService teacherService, RoleRepository roleRepository, MessageRepository messageRepository, MessageService messageService, UserPersonalDataRepos userPersonalDataRepos, AdministrationService administrationService, StudentService studentService) {
        this.fileStorageService = fileStorageService;
        this.userDataService = userDataService;
        this.schoolService = schoolService;
        this.applicationRepository = applicationRepository;
        this.teacherService = teacherService;
        this.roleRepository = roleRepository;
        this.messageRepository = messageRepository;
        this.messageService = messageService;
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.administrationService = administrationService;
        this.studentService = studentService;
    }

    public void createNewApplication(String jsonData, MultipartFile multipartFile, HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        checkIfExistsApplicationFromUser(id);

        if (!jsonData.contains("Приєднання до школи як учень")){
            ApplicationData applicationData = objectMapper.readValue(jsonData, ApplicationData.class);
            Application application = new Application();
            application.setTypeOfApplication(applicationData.getTypeOfApplication());
            application.setUserPersonalData(userDataService.getUserProfile(id));
            application.setSchool(schoolService.getSchool(applicationData.getSchoolName()));
            application.setDocURL(fileStorageService.uploadFiles(multipartFile,uploadDoc));
            application.setSubjects(applicationData.getSubjects());
            application.setDateOfCreate(LocalDateTime.now());
            application.setStatus("Нова");
            applicationRepository.save(application);
            List<UserPersonalData> userPersonalDataList = administrationService.getUserPersonalData(application.getSchool());
            messageService.saveMessage(userPersonalDataList, application.getTypeOfApplication());
        }
        else {
            StudentApplication applicationData = objectMapper.readValue(jsonData, StudentApplication.class);
            Application application = new Application();
            application.setTypeOfApplication(applicationData.getTypeOfApplication());
            application.setUserPersonalData(userDataService.getUserProfile(id));
            application.setSchool(schoolService.getSchool(applicationData.getSchoolName()));
            application.setDocURL(fileStorageService.uploadFiles(multipartFile,uploadDoc));
            application.setSubjects(applicationData.getSubjects());
            application.setDateOfCreate(LocalDateTime.now());
            application.setParentsName(applicationData.getParentsName());
            application.setParentPhone(applicationData.getParentPhone());
            application.setStatus("Нова");
            applicationRepository.save(application);
            List<UserPersonalData> userPersonalDataList = administrationService.getUserPersonalData(application.getSchool());
            messageService.saveMessage(userPersonalDataList, application.getTypeOfApplication());
        }


    }



    public void checkIfExistsApplicationFromUser(String id) throws Exception {
        if (applicationRepository.existsByUserPersonalData(userDataService.getUserProfile(id))
                && applicationRepository.findByUserPersonalData(userDataService.getUserProfile(id)).getStatus().equals("Відхилено")){
            throw new Exception("Вашу заявку відхилено. Зверніться до адміністратора навчального закладу");
        }
        else if (applicationRepository.existsByUserPersonalData(userDataService.getUserProfile(id))
                && applicationRepository.findByUserPersonalData(userDataService.getUserProfile(id)).getStatus().equals("Нова")){
            throw new Exception("Ваша заявка подана. Очікуйте на підтвердження");
        }
        else if (applicationRepository.existsByUserPersonalData(userDataService.getUserProfile(id))
                && applicationRepository.findByUserPersonalData(userDataService.getUserProfile(id)).getStatus().equals("Схвалено")){
            throw new Exception("Вашу заявку схвалено. Вам надано роль вчителя!!!");
        }
    }



    public List<Application> getAllAppBySchool(School school){
        return applicationRepository.findBySchool(school);
    }

//    public List<Application> getAllApp(School school){
//        List<Application> applications = (List<Application>) applicationRepository.findAll();
//        return applications.stream().filter(a->a.getSchool() == school).collect(Collectors.toList());
//    }

//    public List<Application> getAllApp(School school){
//        return applicationRepository.findAll().stream().filter(a->a.getSchool() == school).collect(Collectors.toList());
//    }

    public Application getApplication(String jsonData) throws JsonProcessingException {
        ApplicationGetData applicationData = objectMapper.readValue(jsonData,ApplicationGetData.class);
        String date = applicationData.getDateOfCreation();
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return applicationRepository.findByUserPersonalData_UserNameAndDateOfCreate(applicationData.getUserName(),dateTime);
    }

    public boolean deleteApplication(String jsonData, HttpServletRequest request) throws JsonProcessingException {
        ApplicationGetData applicationData = getDataFromJson(jsonData);
        String date = applicationData.getDateOfCreation();
        LocalDateTime dateTime = parseDate(date);
        Application application = applicationRepository.findByUserPersonalData_UserNameAndDateOfCreate(applicationData.getUserName(),dateTime);
        try {
            Cookie[] cookies = request.getCookies();
            String id = Arrays.stream(cookies).filter((cookie)->cookie.getName().equals("userId")).collect(Collectors.toList()).get(0).getValue();
            messageRepository.deleteMessage(id);
            applicationRepository.deleteApplication(application.getUserPersonalData());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ApplicationGetData getDataFromJson(String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData,ApplicationGetData.class);
    }

    public boolean approveApplication(String jsonData) throws JsonProcessingException {
        ApplicationGetData data = objectMapper.readValue(jsonData,ApplicationGetData.class);
        LocalDateTime dateTime = LocalDateTime.parse(data.getDateOfCreation());
        Application application = applicationRepository.findByUserPersonalData_UserNameAndDateOfCreate(data.getUserName(),dateTime);
        try {
            if (Objects.equals(application.getTypeOfApplication(), "Приєднання до школи як учень")){
                studentService.createNewStudent(application);
                application.setApproved(true);
                application.setStatus("Схвалено");

            }
            else {
                teacherService.createNewTeacherFromApplication(application);
                application.setApproved(true);
                application.setStatus("Схвалено");
            }
        }catch (Exception e){
            return false;
        }
        List<UserPersonalData> userPersonalDataList = Collections.singletonList(application.getUserPersonalData());
        messageService.saveMessage(userPersonalDataList,"Вашу заявку успішно схвалено");
        return true;
    }

    public void rejectApplication(String jsonData) throws JsonProcessingException {
        ApplicationGetData data = objectMapper.readValue(jsonData,ApplicationGetData.class);
        LocalDateTime dateTime = LocalDateTime.parse(data.getDateOfCreation());
        Application application = applicationRepository.findByUserPersonalData_UserNameAndDateOfCreate(data.getUserName(),dateTime);
        application.setApproved(false);
        application.setStatus("Відхилено");
        List<UserPersonalData> userPersonalDataList = Collections.singletonList(application.getUserPersonalData());
        messageService.saveMessage(userPersonalDataList,"Вашу заявку відхилено!!!");
    }

    public LocalDateTime parseDate(String date){
        return LocalDateTime.parse(date);
    }
}
