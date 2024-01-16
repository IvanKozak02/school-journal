package com.example.demoauth.service;

import com.example.demoauth.models.School;
import com.example.demoauth.models.SchoolCalendar;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.SchoolCalendarData;
import com.example.demoauth.repository.SchoolCalendarRepository;
import com.example.demoauth.repository.UserPersonalDataRepos;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class SchoolCalendarService {

    private UserPersonalDataRepos userPersonalDataRepos;

    private SchoolCalendarRepository schoolCalendarRepository;


    public SchoolCalendarService(UserPersonalDataRepos userPersonalDataRepos, SchoolCalendarRepository schoolCalendarRepository) {
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.schoolCalendarRepository = schoolCalendarRepository;
    }

    public void createNewSchoolCalendarEvents(SchoolCalendarData schoolCalendarData, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("userId")).toList().get(0).getValue();
        UserPersonalData userPersonalData = userPersonalDataRepos.findUserPersonalDataById(id);

        SchoolCalendar schoolCalendar = new SchoolCalendar();
        schoolCalendar.setEventType(schoolCalendarData.getEventType());
        schoolCalendar.setEventName(schoolCalendarData.getEventName());
        schoolCalendar.setEventColor(schoolCalendarData.getEventColor());
        schoolCalendar.setDateOfStartEvent(schoolCalendarData.getDateOfStartEvent().trim());
        if (schoolCalendarData.getDateOfFinishEvent() != null){
            schoolCalendar.setDateOfFinishEvent(schoolCalendarData.getDateOfFinishEvent().trim());
        }
        schoolCalendar.setSchool(userPersonalData.getSchool());

        schoolCalendarRepository.save(schoolCalendar);
    }

    public List<SchoolCalendar> getHolidaysEvents(School school) {
        return schoolCalendarRepository.findByEventTypeAndSchool("Канікули", school);
    }

    public List<SchoolCalendar> getSchoolEvents(School school) {
        return schoolCalendarRepository.findByEventTypeAndSchool("Шкільні події", school);
    }

    public List<SchoolCalendar> getPublicEvents(School school) {
        return schoolCalendarRepository.findByEventTypeAndSchool("Державні свята", school);
    }

    public void updateEvent(String id, SchoolCalendarData schoolCalendar) {
        SchoolCalendar schoolEvent = schoolCalendarRepository.findById(Long.parseLong(id)).get();
//        SchoolCalendar schoolEvent = schoolCalendarRepository.findByEventNameAndDateOfStartEvent(schoolCalendar.getEventName(),schoolCalendar.getDateOfStartEvent());
        schoolEvent.setEventName(schoolCalendar.getEventName());
        schoolEvent.setDateOfStartEvent(schoolCalendar.getDateOfStartEvent());
        if (schoolCalendar.getDateOfFinishEvent() != null){
            schoolEvent.setDateOfFinishEvent(schoolCalendar.getDateOfFinishEvent());
        }
        schoolEvent.setEventColor(schoolCalendar.getEventColor());
        schoolCalendarRepository.save(schoolEvent);
    }

    public void deleteEventByID(String eventId) {
        schoolCalendarRepository.deleteById(Long.parseLong(eventId));
    }

    public List<SchoolCalendar> getAllEvents(School school) {
        return schoolCalendarRepository.findBySchool(school);
    }
}
