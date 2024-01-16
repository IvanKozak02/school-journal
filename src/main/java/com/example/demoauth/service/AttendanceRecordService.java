package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.pojo.ChangeTypeOfN;
import com.example.demoauth.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;

@Service
public class AttendanceRecordService {

    private StructureOfYearRepository structureOfYearRepository;

    private MarkRepository markRepository;

    private StudentRepository studentRepository;

    private UserPersonalDataRepos userPersonalDataRepos;

    private ClassJournalService classJournalService;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final ClassJournalRepository classJournalRepository;

    private ClassRepository classRepository;
    private final ScheduleRepository scheduleRepository;

    public AttendanceRecordService(StructureOfYearRepository structureOfYearRepository, MarkRepository markRepository, StudentRepository studentRepository, UserPersonalDataRepos userPersonalDataRepos, ClassJournalService classJournalService, AttendanceRecordRepository attendanceRecordRepository, ClassJournalRepository classJournalRepository, ClassRepository classRepository,
                                   ScheduleRepository scheduleRepository) {
        this.structureOfYearRepository = structureOfYearRepository;
        this.markRepository = markRepository;
        this.studentRepository = studentRepository;
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.classJournalService = classJournalService;
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.classJournalRepository = classJournalRepository;
        this.classRepository = classRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<String> findAllDates(String semester, School school){
        LocalDate start = LocalDate.parse(structureOfYearRepository.findByNameOfSemAndSchool(semester, school).getStartOfSem());
        LocalDate finish = LocalDate.parse(structureOfYearRepository.findByNameOfSemAndSchool(semester,school).getFinishOfSem());
        List<LocalDate> dates = new ArrayList<>();
        LocalDate day = start;
        int plusToNextDay = 1;

        while (!day.isAfter(finish)){
            if (day.getDayOfWeek() != DayOfWeek.SUNDAY) {
                dates.add(day);
            }
            day = day.plusDays(plusToNextDay);
        }
        List <LocalDate> dates1 = classJournalService.deleteAllHolidays(dates,school);
        return classJournalService.parseDate(dates1.stream().sorted().toList());
    }

    public List<Mark> getAllN(String date, String studentId){
        Student student = studentRepository.findByUserPersonalData(userPersonalDataRepos.findUserPersonalDataById(studentId));
        return markRepository.findByStudentAndDateAndMarkBetween(student,date, -2,-1);
    }

    public List<Mark> getAllNByClass(String studentId){
        Student student = studentRepository.findByUserPersonalData(userPersonalDataRepos.findUserPersonalDataById(studentId));
        Class schoolClass = student.getSchoolClass();
        return markRepository.findByStudent_SchoolClassAndMarkBetween(schoolClass, -2,-1);
    }



    public List<String> getStudents(long id){
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findById(id).get();
        List<Student> students = attendanceRecord.getSchoolClass().getStudents().stream().sorted(Comparator.comparing(s->s.getUserPersonalData().getUserName())).toList();
        return classJournalService.getStudentsName(students);
    }


    public String getClassName(long id) {
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findById(id).get();
        return attendanceRecord.getSchoolClass().getNameOfClass();
    }

    public String getTeacherName(long id) {
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findById(id).get();
        return attendanceRecord.getTeacher().getUserPersonalData().getUserName();
    }

    public List<String> getAllSubjects(String date, String studentId, long classId, int index) {
        String dayOfWeek = getDayOfWeek(date);
        List<Schedule> allSchedules = scheduleRepository.findBySchoolClassAndDayOfWeek(classRepository.findById(classId).get(),dayOfWeek);
        List <Schedule> filterSchedule = checkNumOrDen(allSchedules,index);
        List <Schedule> schedulesWithoutDuplicates = findDuplicatesInList(filterSchedule, studentId);
        return getSubjects(schedulesWithoutDuplicates);
    }

    private List<Schedule> findDuplicatesInList(List<Schedule> filterSchedule, String stId) {
        List<Subject> subjects = new ArrayList<>();
        List<Schedule> schedules = new ArrayList<>(filterSchedule);
        for (int i = 0; i < filterSchedule.size(); i++) {
            Schedule schedule = filterSchedule.get(i);
            subjects.add(filterSchedule.get(i).getSubject());
            for (int j = i+1; j < filterSchedule.size(); j++) {
                if (subjects.contains(filterSchedule.get(j).getSubject()) && filterSchedule.get(j).getClassGroup() != null) {
                    findStudentsGroup(schedule, filterSchedule.get(j),schedules, stId);
                    break;
                }
            }
        }


        return schedules;
    }

    private void findStudentsGroup(Schedule schedule, Schedule schedule1, List<Schedule> filterSchedule, String stId) {
        if (schedule.getClassGroup().getStudents().contains(studentRepository.findByUserPersonalData(userPersonalDataRepos.findUserPersonalDataById(stId)))){
            filterSchedule.remove(schedule);
        }
        else {
            filterSchedule.remove(schedule1);
        }
    }

    private List<String> getSubjects(List<Schedule> filterSchedules) {
        List<Schedule> filterSchedulesSorted = filterSchedules.stream().sorted(Comparator.comparing(Schedule::getLessonNumber)).toList();
        List<String> subjects = new ArrayList<>();
        for (int i = 0; i < filterSchedules.size(); i++) {
            subjects.add((i+1) + ". " + filterSchedulesSorted.get(i).getSubject().getName());
            }
        return subjects;
    }

    private List<Schedule> checkNumOrDen(List<Schedule> allSchedules, int index) {
        String period = checkDate(index);
        return allSchedules.stream().filter(s -> Objects.equals(s.getPeriod(), "Кожен тиждень") || Objects.equals(s.getPeriod(), period)).toList();
    }

    private String checkDate(int index) {
        if (index % 2 == 0){
            return "Чисельник";
        }
        else {
            return "Знаменник";
        }
    }

    private String getDayOfWeek(String date) {
        int year = Year.now().getValue();
        LocalDate localDate = LocalDate.parse(year + "-" + date.substring(3) + "-" + date.substring(0,2));
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return getUkrainianDayOfWeek(dayOfWeek);

    }

    private String getUkrainianDayOfWeek(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek.toString().trim()) {
            case "MONDAY" -> "Понеділок";
            case "TUESDAY" -> "Вівторок";
            case "WEDNESDAY" -> "Середа";
            case "THURSDAY" -> "Четвер";
            case "FRIDAY" -> "П'ятниця";
            case "SATURDAY" -> "Субота";
            default -> "";
        };
    }

    public void changeN(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChangeTypeOfN changeTypeOfN = objectMapper.readValue(jsonData, ChangeTypeOfN.class);
        List<Mark> N = markRepository.findByStudentAndDate(studentRepository.findByUserPersonalData(userPersonalDataRepos.findUserPersonalDataById(changeTypeOfN.getStudentId())),
                                                            changeTypeOfN.getDate());
        updateMarks(N, changeTypeOfN.getMark());
    }

    private void updateMarks(List<Mark> N, String mark) {
        for (Mark value : N) {
            value.setMark(Integer.parseInt(mark));
            markRepository.save(value);
        }
    }
}
