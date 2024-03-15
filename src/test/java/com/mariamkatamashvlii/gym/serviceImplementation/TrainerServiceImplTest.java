package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.auth.Validation;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainerServiceImplTest {
    @Mock
    private TrainerRepository trainerRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private TrainingTypeRepository trainingTypeRepo;
    @Mock
    private Validation validation;
    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Trainer trainer = new Trainer();
        User user = new User();
        user.setUserId(1L);
        trainer.setUser(user);
        TrainingType trainingType = new TrainingType();
        trainer.setSpecialization(trainingType);

        when(userRepo.select(user.getUserId())).thenReturn(user);
        when(trainerRepo.create(any(Trainer.class))).thenReturn(trainer);

        Trainer createdTrainer = trainerService.create(trainer);

        assertNotNull(createdTrainer);
        verify(validation).validateTrainer(trainer, user);
        verify(trainerRepo).create(trainer);
    }

    @Test
    void testUpdate() {
        Trainer trainer = new Trainer();
        User user = new User();
        user.setUserId(1L);
        user.setUsername("trainerUser");
        user.setPassword("password");
        trainer.setUser(user);
        TrainingType trainingType = new TrainingType();
        trainer.setSpecialization(trainingType);

        when(userRepo.select(user.getUserId())).thenReturn(user);
        when(trainerRepo.update(any(Trainer.class))).thenReturn(trainer);

        Trainer updatedTrainer = trainerService.update(trainer);

        assertNotNull(updatedTrainer);
        verify(trainerRepo).update(trainer);
    }

    @Test
    void testSelect() {
        String username = "testUser";
        Trainer expectedTrainer = new Trainer();

        when(trainerRepo.select(username)).thenReturn(expectedTrainer);

        Trainer actualTrainer = trainerService.select(username);

        assertEquals(expectedTrainer, actualTrainer);
        verify(trainerRepo).select(username);
    }

    @Test
    void testActivateTrainer() {
        String username = "activeUser";
        User user = new User();
        user.setUsername(username);
        user.setActive(false);

        when(userRepo.select(username)).thenReturn(user);

        trainerService.activateTrainer(username, true);

        assertTrue(user.isActive());
        verify(userRepo).update(user);
    }

    @Test
    void testDeactivateTrainer() {
        String username = "inactiveUser";
        User user = new User();
        user.setUsername(username);
        user.setActive(true);

        when(userRepo.select(username)).thenReturn(user);

        trainerService.deactivateTrainer(username, false);

        assertFalse(user.isActive());
        verify(userRepo).update(user);
    }

    @Test
    void testChangePassword() {
        String username = "trainer";
        String currentPassword = "pass";
        String newPassword = "newPass";
        User user = new User();
        user.setUsername(username);
        user.setPassword(currentPassword);

        Trainer trainer = new Trainer();
        trainer.setUser(user);

        when(userRepo.select(username)).thenReturn(user);
        when(trainerRepo.select(username)).thenReturn(trainer);

        boolean result = trainerService.changePassword(username, currentPassword, newPassword);

        assertTrue(result, "Password should have been changed successfully.");
        verify(userRepo).update(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals(newPassword, updatedUser.getPassword(), "The new password should match the expected value.");
    }

    @Test
    void testFindAll() {
        List<Trainer> expectedTrainers = new ArrayList<>();

        when(trainerRepo.findAll()).thenReturn(expectedTrainers);

        List<Trainer> actualTrainers = trainerService.findAll();

        assertEquals(expectedTrainers, actualTrainers);
        verify(trainerRepo).findAll();
    }

    @Test
    void testCreateTrainerProfile() {
        long trainingTypeId = 1L;
        long userId = 1L;
        TrainingType trainingType = new TrainingType();
        User user = new User();
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setSpecialization(trainingType);
        expectedTrainer.setUser(user);

        when(trainingTypeRepo.select(trainingTypeId)).thenReturn(trainingType);
        when(userRepo.select(userId)).thenReturn(user);
        when(trainerRepo.create(any(Trainer.class))).thenReturn(expectedTrainer);

        Trainer createdTrainer = trainerService.createTrainerProfile(trainingTypeId, userId);

        assertNotNull(createdTrainer, "The created trainer should not be null.");
        assertEquals(user, createdTrainer.getUser(), "The user of the created trainer should match the expected user.");
        assertEquals(trainingType, createdTrainer.getSpecialization(), "The specialization of the created trainer should match the expected training type.");
        verify(trainingTypeRepo).select(trainingTypeId);
        verify(userRepo).select(userId);
        verify(trainerRepo).create(any(Trainer.class));
    }

    @Test
    void testGetTrainingList() {
        String username = "trainerUsername";
        String password = "validPassword";
        Date fromDate = Date.valueOf("2023-01-01");
        Date toDate = Date.valueOf("2023-01-31");
        String traineeName = "TraineeName";
        Trainer trainer = new Trainer();
        Training training = new Training();
        Trainee trainee = new Trainee();
        User traineeUser = new User();
        traineeUser.setFirstName(traineeName);
        trainee.setUser(traineeUser);
        training.setTrainee(trainee);
        training.setTrainingDate(Date.valueOf("2023-01-15"));
        trainer.setTrainings(new HashSet<>(List.of(training)));

        when(trainerRepo.select(username)).thenReturn(trainer);

        List<Training> result = trainerService.getTrainings(username, password, fromDate, toDate, traineeName);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(trainerRepo).select(username);
    }

    private final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
}
