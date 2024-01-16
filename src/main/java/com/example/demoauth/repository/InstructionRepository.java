package com.example.demoauth.repository;

import com.example.demoauth.models.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long> {

    List<Instruction> findByInstructionClass_Id(long instructionClass_id);
}
