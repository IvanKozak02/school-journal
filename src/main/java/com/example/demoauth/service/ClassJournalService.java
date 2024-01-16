package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.pojo.AdditionalJournalColumnDTO;
import com.example.demoauth.pojo.ColumnDTO;
import com.example.demoauth.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClassJournalService {

    private ClassJournalRepository classJournalRepository;

    private StructureOfYearRepository structureOfYearRepository;

    private ScheduleRepository scheduleRepository;

    private AdditionColumnRepository additionColumnRepository;

    private SchoolCalendarRepository schoolCalendarRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    public ClassJournalService(ClassJournalRepository classJournalRepository, StructureOfYearRepository structureOfYearRepository, ScheduleRepository scheduleRepository, AdditionColumnRepository additionColumnRepository, SchoolCalendarRepository schoolCalendarRepository) {
        this.classJournalRepository = classJournalRepository;
        this.structureOfYearRepository = structureOfYearRepository;
        this.scheduleRepository = scheduleRepository;
        this.additionColumnRepository = additionColumnRepository;
        this.schoolCalendarRepository = schoolCalendarRepository;
    }

    public List<ClassJournal> getAllJournalsForTeacher(Teacher teacher) {
        return classJournalRepository.findBySchedule_Teacher(teacher);
    }

    public List<String> getAllDatesByDays(long id, String semester, School school) {
        ClassJournal classJournal = classJournalRepository.findById(id).get();
        List<Schedule> schedulesRecord;
        if (classJournal.getSchedule().getClassGroup() == null){
            schedulesRecord = scheduleRepository.findByTeacherAndSchoolClassAndSubject(classJournal.getSchedule().getTeacher(),
                    classJournal.getSchedule().getSchoolClass(), classJournal.getSchedule().getSubject());
        }
        else {
            schedulesRecord = scheduleRepository.findByTeacherAndClassGroupAndSubject(classJournal.getSchedule().getTeacher(),
                    classJournal.getSchedule().getClassGroup(), classJournal.getSchedule().getSubject());
        }
        List<String> periods = schedulesRecord.stream().map(Schedule::getPeriod).toList();
        List <String> allDays = getAllDays(schedulesRecord.stream().map(Schedule::getDayOfWeek).toList(),semester,school,periods);
        if (semester.equals("I семестр")){
            allDays.add("I с.");
            allDays.add("Ск.");
        }
        else {
            allDays.add("II с.");
            allDays.add("Ск.");
            allDays.add("Річ.");
            allDays.add("Ск.");
        }
        return allDays;
    }

    public List<String> getAllDays(List<String> days, String semester, School school, List<String> periods) {
        List<String> englishDaysOfWeek = getEnglishDayOfWeek(days);
        LocalDate start = LocalDate.parse(structureOfYearRepository.findByNameOfSemAndSchool(semester, school).getStartOfSem());
        LocalDate finish = LocalDate.parse(structureOfYearRepository.findByNameOfSemAndSchool(semester,school).getFinishOfSem());
        List<LocalDate> dates = new ArrayList<>();

        for (int i = 0; i < englishDaysOfWeek.size(); i++) {
            dates.addAll(findAllDates(start,finish,getDay(englishDaysOfWeek.get(i)),periods.get(i)));

        }

        List<LocalDate> dates1 = deleteAllHolidays(dates.stream().sorted().toList(),school);

       return parseDate(dates1);
    }


    private List<LocalDate> deleteEvenElements(List<LocalDate> dates1) {
        List <LocalDate> list = new ArrayList<>();
        for (int i = 0; i < dates1.size(); i++) {
            if (i % 2 == 0){
                list.add(dates1.get(i));
            }
        }
        return list;
    }
    private List<LocalDate> deleteOddElements(List<LocalDate> dates1) {
        List <LocalDate> list = new ArrayList<>();
        for (int i = 0; i < dates1.size(); i++) {
            if (i % 2 != 0){
                list.add(dates1.get(i));
            }
        }
        return list;
    }


    private int getDay(String day) {
        return switch (day) {
            case "Monday" -> 1;
            case "Tuesday" -> 2;
            case "Wednesday" -> 3;
            case "Thursday" -> 4;
            case "Friday" -> 5;
            case "Saturday" -> 6;
            default -> 0;
        };
    }

    public List<String> parseDate(List<LocalDate> sortDates) {
        List<String> parseDates = new ArrayList<>();

        for (LocalDate date: sortDates) {
            int day = date.get(ChronoField.DAY_OF_MONTH);
            int month = date.get(ChronoField.MONTH_OF_YEAR);
            parseDates.add((day < 10 ? '0' + String.valueOf(day) : String.valueOf(day)) + "." + (month<10 ? '0' + String.valueOf(month) : String.valueOf(month)));
        }

        return parseDates;

    }


    public List<LocalDate> findAllDates(LocalDate start, LocalDate finish, int dayOfWeek, String period){
        LocalDate day = start;
        int plusToNextDay = 1;
        List<LocalDate> dates = new ArrayList<>();

        while (!day.isAfter(finish)){
            if (day.getDayOfWeek() == DayOfWeek.of(dayOfWeek)) {
                plusToNextDay = 7;
                dates.add(day);

            }
            day = day.plusDays(plusToNextDay);
        }
        List<LocalDate> sortDates = dates.stream().sorted().collect(Collectors.toList());
        if (Objects.equals(period, "Чисельник")) {
            dates = deleteEvenElements(sortDates);
        }
        else if (Objects.equals(period, "Знаменник")) {
            dates = deleteOddElements(sortDates);
        }
        else {
            dates = sortDates;
        }

        return dates;
    }


    private List<String> getEnglishDayOfWeek(List<String> days) {

//        Set<String> englishDays = new HashSet<>();
        List<String> englishDays = new ArrayList<>();
        for (String day : days) {
            switch (day) {
                case "Понеділок" -> englishDays.add("Monday");
                case "Вівторок" -> englishDays.add("Tuesday");
                case "Середа" -> englishDays.add("Wednesday");
                case "Четвер" -> englishDays.add("Thursday");
                case "П'ятниця" -> englishDays.add("Friday");
                case "Субота" -> englishDays.add("Saturday");
            }
        }

        return new ArrayList<>(englishDays);
    }


    public List<String> getStudents(long id) {
        List<Student> students = getListOfStudents(id);
        return getStudentsName(students);
    }

    public List <Student> getListOfStudents(long id){
        ClassJournal classJournal = classJournalRepository.findById(id).get();
        List<Student> students;
        if (classJournal.getSchedule().getClassGroup() != null){
            students = classJournal.getSchedule().getClassGroup().getStudents().stream().sorted(Comparator.comparing(s->s.getUserPersonalData().getUserName())).toList();
        }
        else {
            students = new ArrayList<>(classJournal.getSchedule().getSchoolClass().getStudents().stream().sorted(Comparator.comparing(s->s.getUserPersonalData().getUserName())).toList());
        }
        return students;
    }

    public List<String> getStudentsName(List<Student> students) {
        List <String> studentsName = students.stream().map(s -> s.getUserPersonalData().getId() + "-"  +  s.getUserPersonalData().getUserName()).toList();
        List <String> list = new ArrayList<>();
        for (int i = 0; i < studentsName.size(); i++) {
            String s = studentsName.get(i).split("-")[0] + "-" + (i+1) + ". " + studentsName.get(i).split("-")[1];
            list.add(s);
        }
        return list;
    }

    public String getTeacherName(long id) {
        ClassJournal classJournal = classJournalRepository.findById(id).get();
        return classJournal.getSchedule().getTeacher().getUserPersonalData().getUserName();
    }

    public String getClassName(long id) {
        ClassJournal classJournal = classJournalRepository.findById(id).get();
        return classJournal.getSchedule().getSchoolClass().getNameOfClass();
    }

    public int getClassGroup(long id) {
        ClassJournal classJournal = classJournalRepository.findById(id).get();
        if (classJournal.getSchedule().getClassGroup() != null){
            return classJournal.getSchedule().getClassGroup().getNumberOfGroup();
        }
        return 0;
    }

    public String getSubject(long id) {
        ClassJournal classJournal = classJournalRepository.findById(id).get();
        return classJournal.getSchedule().getSubject().getName();
    }

    public void addNewColumnToJournal(String jsonData) throws JsonProcessingException {
        AdditionalJournalColumnDTO additionalJournalColumnDTO = objectMapper.readValue(jsonData,AdditionalJournalColumnDTO.class);
        AdditionalJournalColumn additionalJournalColumn = new AdditionalJournalColumn();
        additionalJournalColumn.setColumnName(additionalJournalColumnDTO.getColumnName());
        additionalJournalColumn.setCanAdd(additionalJournalColumnDTO.isCanAdd());
        additionalJournalColumn.setDate(additionalJournalColumnDTO.getDate().trim());
        additionalJournalColumn.setClassJournal(classJournalRepository.findById(Long.parseLong(additionalJournalColumnDTO.getClassJournal())).get());
        additionColumnRepository.save(additionalJournalColumn);

    }

    public List<AdditionalJournalColumn> getAdditionColumns(long id) {
        return additionColumnRepository.findByClassJournal_Id(id);
    }

    public void insertColumnToList(List<String> allDays, List<AdditionalJournalColumn> additionColumns) {
        for (AdditionalJournalColumn additionColumn : additionColumns) {
            int index = allDays.indexOf(additionColumn.getDate().trim());
            if (index != -1){
                allDays.add(index, additionColumn.getColumnName());
            }
        }
    }

    public AdditionalJournalColumn getAllColumnData(String date, String classJournal, String colName) {
        return additionColumnRepository.findByClassJournal_IdAndDateAndColumnName(Long.parseLong(classJournal),date.trim(), colName);
    }

    public void editColumn(String jsonData) throws JsonProcessingException {
        ColumnDTO columnDTO = objectMapper.readValue(jsonData,ColumnDTO.class);
        AdditionalJournalColumn additionalJournalColumn = additionColumnRepository.findById(Long.parseLong(columnDTO.getColId())).get();
        additionalJournalColumn.setColumnName(columnDTO.getColumnName());
        additionalJournalColumn.setClassJournal(classJournalRepository.findById(Long.parseLong(columnDTO.getClassJournal())).get());
        additionalJournalColumn.setCanAdd(columnDTO.isCanAdd());
        additionalJournalColumn.setDate(columnDTO.getDate());

        additionColumnRepository.save(additionalJournalColumn);

    }

    public List<LocalDate> deleteAllHolidays(List<LocalDate> allDays, School school) {

        List<SchoolCalendar> holidays = schoolCalendarRepository.findByEventTypeAndSchool("Канікули", school);
        List<SchoolCalendar> publicHolidays = schoolCalendarRepository.findByEventTypeAndSchool("Державні свята", school);

        List<LocalDate> localDates = new ArrayList<>(allDays);
        List<LocalDate> dates = new ArrayList<>();
        getHolidayDates(holidays,dates);
        getHolidayDates(publicHolidays,dates);
        localDates.removeAll(dates);

        return localDates;
    }

    public void getHolidayDates(List<SchoolCalendar> days, List<LocalDate> dates) {
        for (SchoolCalendar holiday : days) {
            LocalDate start = LocalDate.parse(holiday.getDateOfStartEvent().substring(6) + '-' + holiday.getDateOfStartEvent().substring(3, 5) + '-' +
                    holiday.getDateOfStartEvent().substring(0, 2));
            if (holiday.getDateOfFinishEvent() == null){
                dates.addAll(getDatesBetweenUsingJava8(start, start).stream().sorted().toList());
            }
            else {
                LocalDate finish = LocalDate.parse(holiday.getDateOfFinishEvent().substring(6) + '-' + holiday.getDateOfFinishEvent().substring(3, 5) + '-' +
                    holiday.getDateOfFinishEvent().substring(0, 2));
                dates.addAll(getDatesBetweenUsingJava8(start, finish).stream().sorted().toList());
            }
        }
    }



    public static List<LocalDate> getDatesBetweenUsingJava8(
            LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate)+1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }
}
