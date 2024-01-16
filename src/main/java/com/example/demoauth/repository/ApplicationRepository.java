package com.example.demoauth.repository;

import com.example.demoauth.models.Application;
import com.example.demoauth.models.School;
import com.example.demoauth.models.UserPersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {

    Application findByUserPersonalData_UserNameAndDateOfCreate(String username, LocalDateTime dateOfCreate);

    List<Application> findBySchool(School school);
    boolean existsByUserPersonalData(UserPersonalData userPersonalData);

    Application findByUserPersonalData(UserPersonalData userPersonalData);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM application WHERE  user_personal_data_id= :user_personal_data_id", nativeQuery = true)
    void deleteApplication(@Param("user_personal_data_id") UserPersonalData user_personal_data_id);

}
