package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepo;

    @Override
    public Training create(Training training) {
        log.info("Created training - {}", training.getTrainingName());
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
