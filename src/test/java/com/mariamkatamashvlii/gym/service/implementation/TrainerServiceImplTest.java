//package com.mariamkatamashvlii.gym.service.implementation;
//
//import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
//import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
//import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
//import com.mariamkatamashvlii.gym.entity.Trainer;
//import com.mariamkatamashvlii.gym.entity.TrainingType;
//import com.mariamkatamashvlii.gym.entity.User;
//import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
//import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
//import com.mariamkatamashvlii.gym.repository.TrainerRepository;
//import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
//import com.mariamkatamashvlii.gym.repository.UserRepository;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class TrainerServiceImplTest {
//    @Mock
//    private TrainerRepository trainerRepo;
//    @Mock
//    private UserRepository userRepo;
//    @Mock
//    private TrainingTypeRepository trainingTypeRepo;
//    @Mock
//    private UsernameGenerator usernameGenerator;
//    @Mock
//    private PasswordGenerator passwordGenerator;
//
//    @InjectMocks
//    private TrainerServiceImpl trainerService;
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    @Test
//    void testGetTrainerProfile_userNotFound() {
//        String username = "nonExistentUser";
//        when(trainerRepo.findByUsername(username)).thenReturn(null);
//
//        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
//            trainerService.getTrainerProfile(username);
//        });
//
//        assertTrue(thrown.getMessage().contains("Trainer not found with username"));
//    }
//
//    @Test
//    void testToggleActivation_userNotFound() {
//        ToggleActivationDTO toggleActivationDTO = new ToggleActivationDTO("nonExistentUser", true);
//        when(userRepo.findByUsername(toggleActivationDTO.getUsername())).thenReturn(null);
//
//        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
//            trainerService.toggleActivation(toggleActivationDTO);
//        });
//
//        assertTrue(thrown.getMessage().contains("User not found for username"));
//    }
//
//
//
//
//}
