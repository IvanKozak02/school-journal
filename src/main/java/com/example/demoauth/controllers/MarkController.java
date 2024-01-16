package com.example.demoauth.controllers;

import com.example.demoauth.models.Mark;
import com.example.demoauth.service.MarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "Контролер, що містить бізнес-логіку роботи з оцінюванням учнів")
public class MarkController {

    private MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @PostMapping("/mark")
    @ApiOperation("Метод, що відповідає за збереження оцінки")
    public ResponseEntity<?> saveMark(@RequestParam("jsonData") String jsonData){
        try {
            markService.saveMark(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("mark/{journalId}")
    @ApiOperation("Метод, що відповідає за отримання всіх оцінок певного журналу")
    public ResponseEntity<?> getAllMarks(@PathVariable long journalId){
        try {
            List<Mark> markList = markService.getAllMarks(journalId);
            return new ResponseEntity<>(markList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/mark")
    @ApiOperation("Метод, що відповідає за видалення оцінки")
    public ResponseEntity<?> deleteN(@RequestParam("jsonData") String jsonData){
        try {
            markService.deleteN(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
