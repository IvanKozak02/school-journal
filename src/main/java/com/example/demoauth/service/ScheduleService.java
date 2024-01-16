package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import com.example.demoauth.pojo.ScheduleData;
import com.example.demoauth.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    private TeacherService teacherService;

    private UserDataService userDataService;

    private SubjectsRepository subjectsRepository;

    private ClassService classService;

    private ClassGroupRepository classGroupRepository;

    private ClassJournalRepository classJournalRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, TeacherService teacherService, UserDataService userDataService, SubjectsRepository subjectsRepository, ClassService classService, ClassGroupRepository classGroupRepository, ClassJournalRepository classJournalRepository,
                           ClassRepository classRepository,
                           MarkRepository markRepository) {
        this.scheduleRepository = scheduleRepository;
        this.teacherService = teacherService;
        this.userDataService = userDataService;
        this.subjectsRepository = subjectsRepository;
        this.classService = classService;
        this.classGroupRepository = classGroupRepository;
        this.classJournalRepository = classJournalRepository;
        this.classRepository = classRepository;
        this.markRepository = markRepository;
    }

    ObjectMapper objectMapper = new ObjectMapper();
    private final ClassRepository classRepository;
    private final MarkRepository markRepository;

    public void saveSchedule(String jsonData) throws Exception {
        ScheduleData scheduleData = objectMapper.readValue(jsonData, ScheduleData.class);
        Teacher teacher = teacherService.getTeacherByUserPersonalData(userDataService.getUserProfile(scheduleData.getTeacher()));
        Subject subject = subjectsRepository.findByName(scheduleData.getSubject());
        Class schoolClass = classService.getClassById(Long.parseLong(scheduleData.getSchoolClass()));
        ClassGroup classGroup = null;
        if (scheduleData.getClassGroup() != null) {
            classGroup = classGroupRepository.findById(Long.parseLong(scheduleData.getClassGroup())).get();
        }

        if (scheduleRepository.existsBySchoolClassAndDayOfWeekAndLessonNumber(schoolClass, scheduleData.getDayOfWeek(),
                scheduleData.getLessonNumber()) && scheduleData.getClassGroup() == null){
            throw new Exception();
        }
        else if (scheduleData.getClassGroup() != null){
            if (scheduleRepository.existsBySchoolClassAndDayOfWeekAndLessonNumberAndClassGroupAndPeriod(schoolClass, scheduleData.getDayOfWeek(),
                    scheduleData.getLessonNumber(), classGroup, scheduleData.getPeriod())){
                throw new Exception();
            }

        }
        createNewSchedule(teacher, subject, schoolClass, classGroup, scheduleData);

//        if (!scheduleRepository.existsBySchoolClassAndSubjectAndLessonNumberAndDayOfWeekAndClassGroup(schoolClass,
//                        subject, scheduleData.getLessonNumber(), scheduleData.getDayOfWeek(), classGroup)){
//            createNewSchedule(teacher, subject, schoolClass, classGroup, scheduleData);
//        }
//        else {
//            throw new Exception("Невірно введений розклад!!!");
//        }

//        if (scheduleRepository.existsBySchoolClassAndSubjectAndLessonNumberAndDayOfWeekAndClassGroupAndPeriod(schoolClass,
//                subject, scheduleData.getLessonNumber(), scheduleData.getDayOfWeek(), classGroup, scheduleData.getPeriod())) {
//            Schedule scheduleRec = scheduleRepository.findBySchoolClassAndSubjectAndLessonNumberAndDayOfWeekAndClassGroupAndPeriod(schoolClass,
//                    subject, scheduleData.getLessonNumber(), scheduleData.getDayOfWeek(), classGroup, scheduleData.getPeriod());
//            scheduleRepository.delete(scheduleRec);
//            createNewSchedule(teacher, subject, schoolClass, classGroup, scheduleData);
//        }
//        else if (scheduleRepository.existsBySchoolClassAndSubjectAndLessonNumberAndDayOfWeekAndClassGroupAndPeriod(schoolClass,
//                    subject, scheduleData.getLessonNumber(), scheduleData.getDayOfWeek(), classGroup, scheduleData.getPeriod()) &&
//                    Objects.equals(scheduleData.getPeriod(), "Чисельник")){
//
//
//                throw new Exception("Невірно введений розклад!!!");
//            }
    }


    public void createNewSchedule(Teacher teacher,Subject subject, Class schoolClass,
                                  ClassGroup classGroup, ScheduleData scheduleData){
            Schedule schedule = new Schedule();
            schedule.setTeacher(teacher);
            schedule.setSubject(subject);
            schedule.setSchoolClass(schoolClass);
            schedule.setClassGroup(classGroup);
            schedule.setDayOfWeek(scheduleData.getDayOfWeek());
            schedule.setLessonNumber(scheduleData.getLessonNumber());
            schedule.setPeriod(scheduleData.getPeriod());

            scheduleRepository.save(schedule);

            if (classGroup != null && !classJournalRepository.existsBySchedule_Teacher_AndSchedule_SubjectAndSchedule_ClassGroup(teacher,
                    subject, classGroup) || classGroup == null && !classJournalRepository.existsBySchedule_Teacher_AndSchedule_SubjectAndSchedule_SchoolClass(teacher,
                    subject, schoolClass)){
                createNewJournal(schedule);
            }
        }

    private void createNewJournal(Schedule schedule) {
        ClassJournal classJournal = new ClassJournal();
        classJournal.setSchedule(schedule);
        classJournalRepository.save(classJournal);
    }

    public List<Schedule> getAllScheduleRecordForTeacher(Teacher teacher) {
        return scheduleRepository.findByTeacher(teacher);
    }

    public void editSchedule(String newSchedule, String oldSchedule) throws JsonProcessingException {
        ScheduleData updateSc = objectMapper.readValue(newSchedule, ScheduleData.class);
        ScheduleData deleteSc = objectMapper.readValue(oldSchedule, ScheduleData.class);

        Teacher teacher = teacherService.getTeacherByUserPersonalData(userDataService.getUserProfile(deleteSc.getTeacher()));
        Subject subject = subjectsRepository.findByName(deleteSc.getSubject());
        Class scClass = classService.getClassById(Long.parseLong(deleteSc.getSchoolClass()));
        ClassGroup classGroup = null;
        if (!Objects.equals(deleteSc.getClassGroup(), "'null'")){
            classGroup = classGroupRepository.findById(Long.parseLong(deleteSc.getClassGroup())).get();
        }


        scheduleRepository.deleteByTeacherAndSubjectAndSchoolClassAndClassGroupAndDayOfWeekAndLessonNumberAndPeriod(teacher, subject,
                scClass, classGroup, deleteSc.getDayOfWeek(), deleteSc.getLessonNumber(), deleteSc.getPeriod());

        Teacher teacherUpd = teacherService.getTeacherByUserPersonalData(userDataService.getUserProfile(updateSc.getTeacher()));
        Subject subjectUpd = subjectsRepository.findByName(updateSc.getSubject());

        Class scClassUpd = classService.getClassById(Long.parseLong(updateSc.getSchoolClass()));
        ClassGroup classGroupUpd = null;
        if (!Objects.equals(updateSc.getClassGroup(), null)){
            classGroupUpd = classGroupRepository.findById(Long.parseLong(updateSc.getClassGroup())).get();
        }

        createNewSchedule(teacherUpd, subjectUpd, scClassUpd, classGroupUpd, updateSc);
    }

    public void deleteSchedule(String jsonData, School school) throws Exception {
        Schedule schedule = null;
        ScheduleData scheduleData = objectMapper.readValue(jsonData,ScheduleData.class);
        if (!Objects.equals(scheduleData.getClassGroup(), "null")){
            schedule = scheduleRepository.findBySchoolClassAndSubjectAndLessonNumberAndDayOfWeekAndClassGroup(
                    classRepository.findByNameOfClassAndSchool(scheduleData.getSchoolClass(),school),
                    subjectsRepository.findByName(scheduleData.getSubject()),scheduleData.getLessonNumber(),
                    scheduleData.getDayOfWeek(),classGroupRepository.findBySchoolClassAndNumberOfGroupAndSubject(
                            classRepository.findByNameOfClassAndSchool(scheduleData.getSchoolClass(),school),
                            Integer.parseInt(scheduleData.getClassGroup()),subjectsRepository.findByName(scheduleData.getSubject())
                    )
            );
        }
        else {
            schedule = scheduleRepository.findBySchoolClassAndSubjectAndLessonNumberAndDayOfWeekAndClassGroup(
                    classRepository.findByNameOfClassAndSchool(scheduleData.getSchoolClass(),school),
                    subjectsRepository.findByName(scheduleData.getSubject()),scheduleData.getLessonNumber(),
                    scheduleData.getDayOfWeek(),null);
        }

        ClassJournal classJournal = classJournalRepository.findBySchedule(schedule);

        if (classJournal != null && markRepository.findByClassJournal(classJournal).isEmpty()){
            classJournalRepository.delete(classJournal);
            scheduleRepository.delete(schedule);
        }
        else {
            throw new Exception();
        }
    }
}

