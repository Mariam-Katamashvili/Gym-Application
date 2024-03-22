package com.mariamkatamashvlii.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.service.TraineeService;
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

import java.sql.Date;
import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TraineeController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TraineeControllerTest {
    @Autowired
    private TraineeController traineeController;

    @MockBean
    private TraineeService traineeService;

    @Test
    void testDelete() throws Exception {
        doNothing().when(traineeService).delete(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/traineeProfile/{username}/delete",
                "janedoe");

        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Trainee deleted successfully"));
    }

    @Test
    void testDelete2() throws Exception {
        doNothing().when(traineeService).delete(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/traineeProfile/{username}/delete",
                "janedoe");
        requestBuilder.contentType("https://example.org/example");

        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Trainee deleted successfully"));
    }

    @Test
    void testToggleActivation() throws Exception {
        doNothing().when(traineeService).toggleActivation(Mockito.<ToggleActivationDTO>any());

        ToggleActivationDTO toggleActivationDTO = new ToggleActivationDTO();
        toggleActivationDTO.setIsActive(true);
        toggleActivationDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(toggleActivationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/traineeProfile/{username}/toggleActivation", "janedoe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Activation status changed"));
    }


    @Test
    void testGetProfile() throws Exception {
        when(traineeService.getTraineeProfile(Mockito.<String>any())).thenReturn(new ProfileResponseDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/traineeProfile/{username}", "janedoe");

        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":null,\"lastName\":null,\"birthday\":null,\"address\":null,\"trainers\":null,\"active\":false}"));
    }

    @Test
    void testGetUnassigned() throws Exception {
        when(traineeService.getUnassignedTrainers(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/traineeProfile/{username}/unassigned",
                "janedoe");

        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testRegistration() throws Exception {
        when(traineeService.registerTrainee(Mockito.<RegistrationRequestDTO>any()))
                .thenReturn(new RegistrationResponseDTO("janedoe", "1234567890"));
        Date birthday = mock(Date.class);
        when(birthday.getTime()).thenReturn(10L);

        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setAddress("42 Main St");
        registrationRequestDTO.setBirthday(birthday);
        registrationRequestDTO.setFirstName("Jane");
        registrationRequestDTO.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(registrationRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/traineeProfile/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"username\":\"janedoe\",\"password\":\"1234567890\"}"));
    }
}
