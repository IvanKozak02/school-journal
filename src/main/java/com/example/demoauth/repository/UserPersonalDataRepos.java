package com.example.demoauth.repository;

import com.example.demoauth.models.Role;
import com.example.demoauth.models.School;
import com.example.demoauth.models.UserPersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface UserPersonalDataRepos extends JpaRepository<UserPersonalData,String> {

    UserPersonalData findUserPersonalDataById(String id);
    boolean existsById(String Id);


    UserPersonalData findUserPersonalDataByEmail(String email);

    List<UserPersonalData> findBySchoolAndUserAuthDataRolesIn(School school, Collection<Set<Role>> userAuthData_roles);


}
