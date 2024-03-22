package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingRequest;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.exception.TraineeNotFoundException;
import com.mariamkatamashvlii.gym.exception.TrainerNotFoundException;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepo;
    private final TraineeRepository traineeRepo;
    private final TrainerRepository trainerRepo;

    @Override
    public void create(TrainingRequest trainingRequest) {
        String transactionId = UUID.randomUUID().toString();
        try {
            log.info("[{}] Creating training", transactionId);
            String traineeUsername = trainingRequest.getTraineeUsername();
            String trainerUsername = trainingRequest.getTrainerUsername();
            Trainee trainee = traineeRepo.findByUsername(traineeUsername);
            Trainer trainer = trainerRepo.findByUsername(trainerUsername);
            if (trainee == null) {
                throw new TraineeNotFoundException("Trainee with username " + traineeUsername + " does not exist");
            }
            if (trainer == null) {
                throw new TrainerNotFoundException("Trainer with username " + trainerUsername + " does not exist");
            }
            Training training = new Training();
            training.setTrainee(trainee);
            training.setTrainer(trainer);
            training.setTrainingName(trainingRequest.getTrainingName());
            training.setTrainingDate(trainingRequest.getDate());
            training.setDuration(trainingRequest.getDuration());
            trainingRepo.save(training);
            log.info("[{}] Training created successfully", transactionId);
        } catch (Exception e) {
            log.error("[{}] Error creating training: {}", transactionId, e.getMessage());
            throw e;
        }

    }

}
