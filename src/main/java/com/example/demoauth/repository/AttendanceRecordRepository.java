package com.example.demoauth.repository;

import com.example.demoauth.models.AttendanceRecord;
import com.example.demoauth.models.Class;
import com.example.demoauth.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

    @Transactional
    void deleteBySchoolClass_Id(long classId);

    AttendanceRecord findByTeacher(Teacher teacher);

    AttendanceRecord findBySchoolClass(Class schoolClass);
}
