package com.example.demoauth.service;

import com.example.demoauth.models.CalendarPlanning;
import com.example.demoauth.models.School;
import com.example.demoauth.pojo.CalendarPlanningData;
import com.example.demoauth.repository.CalendarPlanningRepos;
import com.example.demoauth.repository.ClassJournalRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CalendarPlanningService {

    private ClassJournalRepository classJournalRepository;

    private ClassJournalService classJournalService;

    private CalendarPlanningRepos calendarPlanningRepos;

    ObjectMapper objectMapper = new ObjectMapper();

    public CalendarPlanningService(ClassJournalRepository classJournalRepository, ClassJournalService classJournalService, CalendarPlanningRepos calendarPlanningRepos) {
        this.classJournalRepository = classJournalRepository;
        this.classJournalService = classJournalService;
        this.calendarPlanningRepos = calendarPlanningRepos;
    }

    public void saveCalendarPlanningRecord(String jsonData, School school) throws JsonProcessingException {
        CalendarPlanningData calendarPlanningData = objectMapper.readValue(jsonData, CalendarPlanningData.class);

            CalendarPlanning calendarPlanning = new CalendarPlanning();
            calendarPlanning.setClassJournal(classJournalRepository.findById(Long.parseLong(calendarPlanningData.getJournalId())).get());
            calendarPlanning.setHomework(calendarPlanningData.getHomework());
            calendarPlanning.setSemester(calendarPlanningData.getSemester());

            if (!Objects.equals(calendarPlanningData.getDateOfHomework(), "n")){
                calendarPlanning.setDateOfHomework(calendarPlanningData.getDateOfHomework());
            }
            else {
                calendarPlanning.setDateOfHomework(getDateOfHomeWork(calendarPlanningData.getDateOfLesson(),school,
                        Long.parseLong(calendarPlanningData.getJournalId()),calendarPlanningData.getSemester()));
            }


            calendarPlanning.setLessonTopic(calendarPlanningData.getLessonTopic());
            calendarPlanning.setDateOfLesson(getDate(calendarPlanningData.getDateOfLesson(),school,
                    Long.parseLong(calendarPlanningData.getJournalId()),calendarPlanningData.getSemester()));
            calendarPlanningRepos.save(calendarPlanning);
        }



    public String getDateOfHomeWork(String dateOfLesson, School school, long journalId, String semester) {
        LocalDate today = LocalDate.now();
        int count = Integer.parseInt(dateOfLesson);
        List<String> dates = classJournalService.getAllDatesByDays(journalId,semester,school);
        return dates.get(count+1) + "." + today.getYear();
    }


    public String getDate(String dateOfLesson, School school, long journalId, String semester) {
        int count = Integer.parseInt(dateOfLesson);
        List<String> dates = classJournalService.getAllDatesByDays(journalId,semester,school);
        return dates.get(count);
    }

    public void editCalendarPlan(String oldObj, String newObj, School school) throws JsonProcessingException {
        CalendarPlanningData calendarPlanningOldData = objectMapper.readValue(oldObj,CalendarPlanningData.class);
        CalendarPlanningData calendarPlanningNewData = objectMapper.readValue(newObj,CalendarPlanningData.class);
        CalendarPlanning calendarPlanning1 = calendarPlanningRepos.findByClassJournalAndDateOfLessonAndLessonTopicAndHomeworkAndDateOfHomework(
                classJournalRepository.findById(Long.parseLong(calendarPlanningOldData.getJournalId())).get(),
                calendarPlanningOldData.getDateOfLesson(),
                calendarPlanningOldData.getLessonTopic(),
                calendarPlanningOldData.getHomework(),
                calendarPlanningOldData.getDateOfHomework());

        calendarPlanningRepos.delete(calendarPlanning1);

        CalendarPlanning calendarPlanning = new CalendarPlanning();
        calendarPlanning.setHomework(calendarPlanningNewData.getHomework());
        calendarPlanning.setLessonTopic(calendarPlanningNewData.getLessonTopic());
        calendarPlanning.setClassJournal(classJournalRepository.findById(Long.parseLong(calendarPlanningNewData.getJournalId())).get());
        calendarPlanning.setDateOfLesson(calendarPlanningNewData.getDateOfLesson());
        if (Objects.equals(calendarPlanningNewData.getDateOfHomework(), "n")){
            List<String> dates = classJournalService.getAllDatesByDays(Long.parseLong(calendarPlanningNewData.getJournalId()),calendarPlanningNewData.getSemester(),school);
            calendarPlanning.setDateOfHomework(dates.get(dates.indexOf(calendarPlanningNewData.getDateOfLesson())+1) + "." + LocalDate.now().getYear());
        }
        else {
            calendarPlanning.setDateOfHomework(calendarPlanningNewData.getDateOfHomework());
        }
        calendarPlanning.setSemester(calendarPlanningNewData.getSemester());
        calendarPlanningRepos.save(calendarPlanning);
    }


    public String findNumberOfSemester(LocalDate today, List<String> firstSem, List<String> secondSem, boolean all) {
        List<LocalDate> firstSemester = getDates(firstSem, 1, all);
        List<LocalDate> secondSemester = getDates(secondSem, 2, all);

        if (today.isBefore(firstSemester.get(firstSemester.size()-1)) && today.isAfter(firstSemester.get(0))){
            return "I семестр";
        }

        if (today.isBefore(secondSemester.get(secondSemester.size()-1)) && today.isAfter(secondSemester.get(0))){
            return "II семестр";
        }

        return "I семестр";
    }

    private List<LocalDate> getDates(List<String> sem, int semN, boolean all) {
        List<LocalDate> dates = new ArrayList<>();
        if (semN ==1 && !all){
            for (int i = 0; i < sem.size()-2; i++) {
                dates.add(LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(sem.get(i).substring(3)), Integer.parseInt(sem.get(i).substring(0, 2))));
            }
        }
        else if (semN == 1){
            for (String s : sem) {
                dates.add(LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(s.substring(3)), Integer.parseInt(s.substring(0, 2))));
            }
        }
        else if (semN == 2 && !all){
            for (int i = 0; i < sem.size()-4; i++) {
                dates.add(LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(sem.get(i).substring(3)), Integer.parseInt(sem.get(i).substring(0, 2))));
            }
        }

        else if (semN == 2){
            for (String s : sem) {
                dates.add(LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(s.substring(3)), Integer.parseInt(s.substring(0, 2))));
            }
        }

        return dates;
    }
}

