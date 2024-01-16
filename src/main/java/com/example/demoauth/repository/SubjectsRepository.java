package com.example.demoauth.repository;

import com.example.demoauth.models.School;
import com.example.demoauth.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubjectsRepository extends JpaRepository<Subject, Long> {

    Subject findByName(String name);

    boolean existsByName(String name);
}
