package com.example.demoauth.repository;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.StudentMotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMotionsRepository extends JpaRepository<StudentMotions,Long> {

    List<StudentMotions> findByScClass_Id(long scClassId);

    StudentMotions findByCharacteristicAndScClass_Id(String characteristic, Long scClass_id);
}
