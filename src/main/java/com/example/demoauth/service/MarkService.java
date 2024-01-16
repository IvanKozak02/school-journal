package com.example.demoauth.service;

import com.example.demoauth.models.ClassJournal;
import com.example.demoauth.models.Mark;
import com.example.demoauth.models.Student;
import com.example.demoauth.pojo.MarkData;
import com.example.demoauth.repository.ClassJournalRepository;
import com.example.demoauth.repository.MarkRepository;
import com.example.demoauth.repository.StudentRepository;
import com.example.demoauth.repository.UserPersonalDataRepos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {

    ObjectMapper objectMapper = new ObjectMapper();

    private final MarkRepository markRepository;

    private final ClassJournalRepository classJournalRepository;

    private final StudentRepository studentRepository;

    private final UserPersonalDataRepos userPersonalDataRepos;

    public MarkService(MarkRepository markRepository, ClassJournalRepository classJournalRepository, StudentRepository studentRepository, UserPersonalDataRepos userPersonalDataRepos) {
        this.markRepository = markRepository;
        this.classJournalRepository = classJournalRepository;
        this.studentRepository = studentRepository;
        this.userPersonalDataRepos = userPersonalDataRepos;
    }

    public void saveMark(String jsonData) throws JsonProcessingException {
        MarkData markData = objectMapper.readValue(jsonData,MarkData.class);
        Mark mark = new Mark();
        mark.setMark(markData.getMark());
        mark.setTypeOfMark(markData.getTypeOfMark());
        mark.setDate(markData.getDate());
        mark.setClassJournal(classJournalRepository.findById(markData.getClassJournal()).get());
        mark.setStudent(studentRepository.findByUserPersonalData(userPersonalDataRepos.findUserPersonalDataById(markData.getStudent())));
        mark.setColName(markData.getColName());
        if (markRepository.existsMarkByDateAndColNameAndStudentAndClassJournal(mark.getDate(), mark.getColName(),mark.getStudent(),mark.getClassJournal())){
            Mark mark1 = markRepository.findByDateAndColNameAndStudentAndClassJournal(mark.getDate(),mark.getColName(),mark.getStudent(),mark.getClassJournal());
            mark1.setTypeOfMark(mark.getTypeOfMark());
            mark1.setStudent(mark.getStudent());
            mark1.setMark(mark.getMark());
            mark1.setDate(mark.getDate());
            mark1.setClassJournal(mark.getClassJournal());
            mark1.setColName(mark.getColName());
            markRepository.save(mark1);

        }
        else {
            markRepository.save(mark);
        }
    }

    public List<Mark> getAllMarks(long journalId) {
        return markRepository.findByClassJournal(classJournalRepository.findById(journalId).get());
    }

    public void deleteN(String jsonData) throws JsonProcessingException {
        MarkData markData = objectMapper.readValue(jsonData, MarkData.class);
        Mark mark = markRepository.findByDateAndColNameAndStudentAndClassJournal(markData.getDate(), markData.getColName(), studentRepository.findByUserPersonalData(userPersonalDataRepos.findUserPersonalDataById(markData.getStudent())), classJournalRepository.findById(markData.getClassJournal()).get());
        if (mark != null) {
            markRepository.delete(mark);
        }
    }
}
