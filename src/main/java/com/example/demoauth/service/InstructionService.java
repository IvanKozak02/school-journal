package com.example.demoauth.service;

import com.example.demoauth.models.Instruction;
import com.example.demoauth.pojo.InstructionData;
import com.example.demoauth.repository.ClassRepository;
import com.example.demoauth.repository.InstructionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructionService {

    private InstructionRepository instructionRepository;

    private ClassRepository classRepository;

    public InstructionService(InstructionRepository instructionRepository, ClassRepository classRepository) {
        this.instructionRepository = instructionRepository;
        this.classRepository = classRepository;
    }

    public List<Instruction> getAllInstruction(long classId) {
        return instructionRepository.findByInstructionClass_Id(classId);
    }

    public void saveInstruction(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        InstructionData instructionData = objectMapper.readValue(jsonData,InstructionData.class);
        Instruction instruction = new Instruction();
        instruction.setInstructionClass(classRepository.findById(Long.parseLong(instructionData.getClassId())).get());
        instruction.setInstructionDate(instructionData.getInstructionDate());
        instruction.setInstructionSubject(instructionData.getInstructionSubject());
        instructionRepository.save(instruction);
    }

    public void deleteInstruction(long id) {
        instructionRepository.delete(instructionRepository.findById(id).get());
    }
}
