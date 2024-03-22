package com.mariamkatamashvlii.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingRequest;
import com.mariamkatamashvlii.gym.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    @Test
    void addTraining_ReturnsOk() throws Exception {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setTraineeUsername("traineeUser");
        trainingRequest.setTrainerUsername("trainerUser");
        trainingRequest.setTrainingName("Yoga");
        LocalDate trainingDate = LocalDate.of(2024, 3, 20);
        trainingRequest.setDate(trainingDate);
        trainingRequest.setDuration(60);

        doNothing().when(trainingService).create(any(TrainingRequest.class));

        mockMvc.perform(post("/trainings/add")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Training added successfully"));

        verify(trainingService).create(any(TrainingRequest.class));
    }
}