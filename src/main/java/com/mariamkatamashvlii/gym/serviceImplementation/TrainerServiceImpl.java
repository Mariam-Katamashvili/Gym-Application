package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.auth.Validation;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepo;
    private final UserRepository userRepo;
    private final TrainingTypeRepository trainingTypeRepo;
    private final Validation validation;

    @Override
    public Trainer create(Trainer trainer) {
        User user = userRepo.select(trainer.getUser().getUserId());
        validation.validateTrainer(trainer, user);
        log.info("Created trainer with username: {}", trainer.getUser().getUsername());
        return trainerRepo.create(trainer);
    }

    @Override
    public Trainer update(Trainer trainer) {
        User user = userRepo.select(trainer.getUser().getUserId());
        if (trainer.getSpecialization() != null && user != null) {
            log.info("Created trainer with username: {}", trainer.getUser().getUsername());
            return trainerRepo.update(trainer);
        }
        return null;
    }

    @Override
    public Trainer select(String username) {
        Trainer trainer = trainerRepo.select(username);
        if (trainer != null) {
            log.info("Selecting trainer - {}", username);
            return trainer;
        } else {
            log.info("Could not select trainer - {}", username);
            return null;
        }
    }

    @Override
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        Trainer trainer = trainerRepo.select(username);
        if (trainer != null && trainer.getUser().getPassword().equals(currentPassword)) {
            User user = trainer.getUser();
            user.setPassword(newPassword);
            userRepo.update(user);
            log.info("Changed password for - {}", username);
            return true;
        } else {
            log.info("Could not change password for - {}", username);
            return false;
        }
    }

    @Override
    public void activateTrainer(String username, boolean isActive) {
        toggleActivation(username, isActive);
        log.info("Set activation to true for - {}", username);
    }

    @Override
    public void deactivateTrainer(String username, boolean isActive) {
        toggleActivation(username, isActive);
        log.info("Set activation to false for - {}", username);
    }

    private void toggleActivation(String username, boolean isActive) {
        User user = userRepo.select(username);
        user.setActive(isActive);
        userRepo.update(user);
    }

    @Override
    public List<Trainer> findAll() {
        log.info("Selecting all trainers");
        return trainerRepo.findAll();
    }

    @Override
    public Trainer createTrainerProfile(long trainingTypeId, long userId) {
        TrainingType type = trainingTypeRepo.select(trainingTypeId);
        User user = userRepo.select(userId);
        Trainer trainer = Trainer.builder()
                .specialization(type)
                .user(user)
                .build();
        trainer.setUser(user);
        log.info("Creating trainer profile for - {}", trainer.getUser().getUsername());
        return trainerRepo.create(trainer);
    }

    @Override
    public List<Training> getTrainings(String username, String password, Date fromDate, Date toDate, String traineeName) {
        Trainer trainer = trainerRepo.select(username);
        if (trainer == null || trainer.getTrainings() == null) {
            log.info("No trainings found or trainer does not exist for username: {}", username);
            return List.of();
        }
        return trainer.getTrainings().stream()
                .filter(t -> isBetween(t.getTrainingDate(), fromDate, toDate))
                .filter(t -> t.getTrainee().getUser().getFirstName().equals(traineeName))
                .toList();

    }

    public boolean isBetween(Date trainingdate, Date fromDate, Date toDate) {
        return trainingdate.compareTo(fromDate) >= 0 && trainingdate.compareTo(toDate) <= 0;
    }
}
