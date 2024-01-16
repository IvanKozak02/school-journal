package com.example.demoauth.controllers;

import com.example.demoauth.models.Schedule;
import com.example.demoauth.models.Teacher;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.service.ScheduleService;
import com.example.demoauth.service.TeacherService;
import com.example.demoauth.service.UserDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ScheduleControllerTest {

    @Mock
    UserDataService userDataService;

    @Mock
    TeacherService teacherService;

    @Mock
    ScheduleService scheduleService;

    @InjectMocks
    ScheduleController scheduleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).build();
    }

    @Test
    void getAllScheduleRecords() throws Exception {
        UserPersonalData userPersonalData = new UserPersonalData();
        Teacher teacher = new Teacher();

        Mockito.when(userDataService.getUserProfile("1")).thenReturn(userPersonalData);
        Mockito.when(teacherService.getTeacherByUserPersonalData(userPersonalData)).thenReturn(teacher);
        Mockito.when(scheduleService.getAllScheduleRecordForTeacher(teacher)).thenReturn(getSchedule());

        mockMvc.perform(get("/schedulerecord")
                        .param("teacherId", "123")
                        .contentType("application/json"))
                        .andExpect(status().isOk());

    }

    public List<Schedule> getSchedule(){
        Schedule schedule = new Schedule();
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();
        Schedule schedule3 = new Schedule();
        Schedule schedule4 = new Schedule();
        return List.of(schedule1, schedule2, schedule3,schedule4,schedule);
    }
}