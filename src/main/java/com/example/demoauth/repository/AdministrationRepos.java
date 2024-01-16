package com.example.demoauth.repository;

import com.example.demoauth.models.Administration;
import com.example.demoauth.models.School;
import com.example.demoauth.models.UserPersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrationRepos extends JpaRepository<Administration, Long> {

    List<Administration> findBySchool(School school);

    Administration findByUserPersonalData(UserPersonalData userPersonalData);

    List<Administration> findByUserPersonalDataSchool(School school);

}
