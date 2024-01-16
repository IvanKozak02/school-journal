package com.example.demoauth.service;

import com.example.demoauth.models.AttendanceRecord;
import com.example.demoauth.models.Mark;
import com.example.demoauth.models.Student;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.AttendanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VisitStatisticService {

    private AttendanceRecordService attendanceRecordService;

    private AttendanceRecordRepository attendanceRecordRepository;

    public VisitStatisticService(AttendanceRecordService attendanceRecordService, AttendanceRecordRepository attendanceRecordRepository) {
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    public List<String> getCountOfNForStudents(long time, UserPersonalData userPersonalData, long id) {
        AttendanceRecord attendanceRecord = attendanceRecordRepository.findById(id).get();
        List<Student> students = attendanceRecord.getSchoolClass().getStudents().stream().toList();
        List<String> studentsList = attendanceRecordService.getStudents(id);
        String schoolPeriod = getSchoolPeriod(time);
        List<String> datesOfPeriod = new ArrayList<>();

        if (schoolPeriod.equals("Рік")){
            datesOfPeriod.addAll(attendanceRecordService.findAllDates("I семестр",userPersonalData.getSchool()));
            datesOfPeriod.addAll(attendanceRecordService.findAllDates("II семестр",userPersonalData.getSchool()));
        }
        else {
            datesOfPeriod = attendanceRecordService.findAllDates(schoolPeriod,userPersonalData.getSchool());
        }

        // якщо рік то окремо витягнути дати для першого семестру, окремо для другого; об'єднати в один список і передати в функцію getN()


        List <Mark> allN = attendanceRecordService.getAllNByClass(students.get(0).getUserPersonalData().getId());
        Map <Student, Map<String,Integer>> countOfStudentN = getN(students, datesOfPeriod, allN);

        return getNData(studentsList, countOfStudentN);
    }

    private List<String> getNData(List<String> studentsList, Map<Student, Map<String, Integer>> countOfStudentN) {
        List<String> studentListAndData = new ArrayList<>();
        for (String student : studentsList) {
            for (int j = 0; j < countOfStudentN.size(); j++) {
                Student firstKey = (Student) countOfStudentN.keySet().toArray()[j];
                if (Objects.equals(student.split("-")[0], firstKey.getUserPersonalData().getId())) {
                    Map<String, Integer> valueForFirstKey = countOfStudentN.get(firstKey);
                    studentListAndData.add(student.split("-")[0] + "-" + valueForFirstKey.get("Н") + "-" + valueForFirstKey.get("ХВ") + "-" + student.split("-")[1]);
                }

            }
        }
        return studentListAndData;
    }

    private Map <Student, Map<String,Integer>> getN(List<Student> students, List<String> datesOfPeriod, List<Mark> allN) {
        Map <Student, Map<String,Integer>> countOfStudentN = new HashMap<>();
        for (Student student : students) {
            Map<String,Integer> Ns = new HashMap<>();
            Ns.put("Н",0);
            Ns.put("ХВ",0);
            countOfStudentN.put(student,Ns);
            for (Mark mark : allN) {
                if (Objects.equals(mark.getStudent().getId(), student.getId()) &&
                        datesOfPeriod.contains(mark.getDate()) && mark.getMark() == -1) {
                    Ns.put("Н", Ns.get("Н") + 1);
                    countOfStudentN.put(student, Ns);
                }
                else if (Objects.equals(mark.getStudent().getId(), student.getId()) &&
                        datesOfPeriod.contains(mark.getDate()) && mark.getMark() == -2) {
                    Ns.put("ХВ", Ns.get("ХВ") + 1);
                    countOfStudentN.put(student, Ns);
                }
            }
        }
        return countOfStudentN;
    }

    private String getSchoolPeriod(long time) {
        return switch ((int) time) {
            case 1 -> "I семестр";
            case 2 -> "II семестр";
            case 3 -> "Рік";
            default -> "";
        };
    }
}
