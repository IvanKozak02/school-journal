package com.example.demoauth.repository;

import com.example.demoauth.models.Certificates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepos extends JpaRepository<Certificates,Long> {

    List<Certificates> findAllByUserPersonalDataId(String id);
    void deleteById(long id);


}
