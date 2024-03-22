package com.mariamkatamashvlii.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainerTrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TrainerController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TrainerControllerTest {
    @Autowired
    private TrainerController trainerController;

    @MockBean
    private TrainerService trainerService;

    @Test
    void testGetProfile() throws Exception {

        when(trainerService.getTrainerProfile(Mockito.<String>any())).thenReturn(new ProfileResponseDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/trainerProfile/{username}", "janedoe");

        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":null,\"lastName\":null,\"specialization\":null,\"isActive\":null,\"trainees\":null}"));
    }

    @Test
    void testToggleActivation() throws Exception {
        doNothing().when(trainerService).toggleActivation(Mockito.<ToggleActivationDTO>any());

        ToggleActivationDTO toggleActivationDTO = new ToggleActivationDTO();
        toggleActivationDTO.setIsActive(true);
        toggleActivationDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(toggleActivationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/trainerProfile/{username}/toggleActivation", "janedoe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Activation status changed"));
    }

    @Test
    void testRegistration() throws Exception {
        when(trainerService.registerTrainer(Mockito.<RegistrationRequestDTO>any()))
                .thenReturn(new RegistrationResponseDTO("janedoe", "1234567890"));

        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setFirstName("Jane");
        registrationRequestDTO.setLastName("Doe");
        TrainingTypeDTO specialization = TrainingTypeDTO.builder()
                .trainingTypeId(1L)
                .trainingTypeName("Training Type Name")
                .build();
        registrationRequestDTO.setSpecialization(specialization);
        String content = (new ObjectMapper()).writeValueAsString(registrationRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/trainerProfile/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"username\":\"janedoe\",\"password\":\"1234567890\"}"));
    }

    @Test
    void testTrainerTrainings() throws Exception {
        when(trainerService.getTrainings(Mockito.<TrainerTrainingsRequestDTO>any())).thenReturn(new ArrayList<>());

        TrainerTrainingsRequestDTO trainerTrainingsRequestDTO = new TrainerTrainingsRequestDTO();
        trainerTrainingsRequestDTO.setFromDate(null);
        trainerTrainingsRequestDTO.setToDate(null);
        trainerTrainingsRequestDTO.setTraineeName("Trainee Name");
        trainerTrainingsRequestDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(trainerTrainingsRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/trainerProfile/{username}/getTrainings", "janedoe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdate() throws Exception {
        when(trainerService.updateProfile(Mockito.<UpdateRequestDTO>any())).thenReturn(new UpdateResponseDTO());

        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
        updateRequestDTO.setFirstName("Jane");
        updateRequestDTO.setIsActive(true);
        updateRequestDTO.setLastName("Doe");
        TrainingTypeDTO specialization = TrainingTypeDTO.builder()
                .trainingTypeId(1L)
                .trainingTypeName("Training Type Name")
                .build();
        updateRequestDTO.setSpecialization(specialization);
        updateRequestDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(updateRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/trainerProfile/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"username\":null,\"firstName\":null,\"lastName\":null,\"specialization\":null,\"isActive\":null,\"trainees"
                                        + "\":null}"));
    }
}
