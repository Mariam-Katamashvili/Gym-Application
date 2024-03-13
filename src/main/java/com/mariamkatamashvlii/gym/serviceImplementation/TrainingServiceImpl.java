package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepo;
    private final TraineeRepository traineeRepo;
    private final TrainerRepository trainerRepo;

    @Override
    public Training create(String traineeUsername, String trainerUsername, String trainingName, Date date, Number duration) {
        Trainee trainee = traineeRepo.select(traineeUsername);
        Trainer trainer = trainerRepo.select(trainerUsername);
        if (trainee == null) {
            throw new IllegalArgumentException("Trainee with username " + traineeUsername + " does not exist");
        }
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer with username " + trainerUsername + " does not exist");
        }
        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName(trainingName);
        training.setTrainingDate(date);
        training.setDuration(duration);
        return trainingRepo.create(training);
    }

    @Override
    public Training update(Training training) {
        log.info("Updated training - {}", training.getTrainingName());
        return trainingRepo.update(training);
    }

    @Override
    public void delete(long id) {
        trainingRepo.delete(id);
        log.info("Deleted training with id {}", id);
    }

    @Override
    public Training select(long id) {
        log.info("Selecting training with id {}", id);
        return trainingRepo.select(id);
    }

    @Override
    public List<Training> findAll() {
        log.info("Returning all trainings");
        return trainingRepo.findAll();
    }
}
