package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingRequest;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.exception.TraineeNotFoundException;
import com.mariamkatamashvlii.gym.exception.TrainerNotFoundException;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {
    @Mock
    private TrainingRepository trainingRepo;

    @Mock
    private TraineeRepository traineeRepo;

    @Mock
    private TrainerRepository trainerRepo;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private TrainingRequest trainingRequest;
    private Trainee trainee;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainingRequest = new TrainingRequest();
        trainingRequest.setTraineeUsername("traineeUser");
        trainingRequest.setTrainerUsername("trainerUser");
        trainingRequest.setTrainingName("Yoga");
        LocalDate trainingDate = LocalDate.of(2024, 3, 20);
        trainingRequest.setDate(trainingDate);
        trainingRequest.setDuration(60);

        trainee = new Trainee();
        trainer = new Trainer();
    }

    @Test
    void createTraining_Success() {
        when(traineeRepo.findByUsername("traineeUser")).thenReturn(trainee);
        when(trainerRepo.findByUsername("trainerUser")).thenReturn(trainer);

        trainingService.create(trainingRequest);

        verify(trainingRepo).save(any());
    }

    @Test
    void createTraining_ThrowsTraineeNotFoundException() {
        when(traineeRepo.findByUsername("traineeUser")).thenReturn(null);

        assertThrows(TraineeNotFoundException.class, () -> trainingService.create(trainingRequest));
    }

    @Test
    void createTraining_ThrowsTrainerNotFoundException() {
        when(traineeRepo.findByUsername("traineeUser")).thenReturn(trainee);
        when(trainerRepo.findByUsername("trainerUser")).thenReturn(null);

        assertThrows(TrainerNotFoundException.class, () -> trainingService.create(trainingRequest));
    }

}
