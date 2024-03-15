package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.auth.Validation;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.TraineeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepo;
    private final UserRepository userRepo;
    private final TrainerRepository trainerRepo;
    private final TrainingRepository trainingRepository;
    private final Validation validation;

    @Override
    public Trainee create(Trainee trainee) {
        User user = userRepo.select(trainee.getUser().getUserId());
        validation.validateTrainee(trainee, user);
        log.info("Created trainee - {}", trainee.getUser().getUsername());
        return traineeRepo.create(trainee);
    }

    @Override
    public Trainee update(Trainee trainee) {
        User user = userRepo.select(trainee.getUser().getUserId());
        if (user != null && trainee.getBirthday() != null && trainee.getAddress() != null) {
            log.info("Updated trainee - {}", trainee.getUser().getUsername());
            return traineeRepo.update(trainee);
        }
        return null;
    }

    @Override
    public void delete(String username) {
        Trainee trainee = traineeRepo.select(username);
        if (trainee == null) {
            log.info("Trainee does not exist for username: {}", username);
            return;
        }

        Set<Training> trainings = trainee.getTrainings();
        for (Training t : trainings) {
            trainingRepository.delete(t.getId());
        }
        traineeRepo.delete(username);
        log.info("Deleted trainee with username {}", username);
    }

    @Override
    public Trainee select(String username) {
        Trainee trainee = traineeRepo.select(username);
        if (trainee != null) {
            log.info("Selecting trainee - {}", username);
            return trainee;
        } else {
            log.info("Could not select trainee - {}", username);
            return null;
        }
    }

    @Override
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        Trainee trainee = traineeRepo.select(username);
        if (trainee != null && trainee.getUser().getPassword().equals(currentPassword)) {
            User user = trainee.getUser();
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
    public void activateTrainee(String username, boolean isActive) {
        toggleActivation(username, true);
        log.info("Set activation to true for - {}", username);
    }

    @Override
    public void deactivateTrainee(String username, boolean isActive) {
        toggleActivation(username, false);
        log.info("Set activation to false for - {}", username);
    }

    private void toggleActivation(String username, boolean isActive) {
        User user = userRepo.select(username);
        user.setActive(isActive);
        userRepo.update(user);
    }

    @Override
    public void updateTrainers(String username, List<Trainer> trainers) {
        Trainee trainee = traineeRepo.select(username);
        if (trainee != null) {
            trainee.setTrainers(trainers);
            traineeRepo.update(trainee);
            log.info("Updated trainer list for trainee - {}", username);
        }
    }

    @Override
    public List<Trainee> findAll() {
        log.info("Returning all trainees");
        return traineeRepo.findAll();
    }

    @Override
    public Trainee createTraineeProfile(Date birthday, String address, long userId) {
        User user = userRepo.select(userId);
        Trainee trainee = Trainee.builder()
                .birthday(birthday)
                .address(address)
                .user(user)
                .build();
        trainee.setUser(user);
        log.info("Creating trainee profile for - {}", trainee.getUser().getUsername());
        return create(trainee);
    }

    @Override
    public List<Training> getTrainings(String username, Date fromDate, Date toDate, String trainerName, TrainingType trainingType) {
        Trainee trainee = traineeRepo.select(username);
        if (trainee == null || trainee.getTrainings() == null) {
            log.info("No trainings found or trainee does not exist for username: {}", username);
            return List.of();
        }

        return trainee.getTrainings().stream()
                .filter(t -> isBetween(t.getTrainingDate(), fromDate, toDate))
                .filter(t -> t.getTrainer().getUser().getFirstName().equals(trainerName))
                .filter(t -> t.getTrainingType().getTrainingTypeName().equals(trainingType.getTrainingTypeName()))
                .toList();
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String username) {
        List<Trainer> notAssignedTrainers = new ArrayList<>();
        Trainee trainee = traineeRepo.select(username);
        if (trainee != null) {
            List<Trainer> traineeTrainers = trainee.getTrainers();
            List<Trainer> allTrainers = trainerRepo.findAll();
            for (Trainer t : allTrainers) {
                if (!traineeTrainers.contains(t)) {
                    notAssignedTrainers.add(t);
                }
            }
        }
        log.info("Returning not assigned trainer list for trainee {}", username);
        return notAssignedTrainers;
    }

    public boolean isBetween(Date trainingdate, Date fromDate, Date toDate) {
        return trainingdate.compareTo(fromDate) >= 0 && trainingdate.compareTo(toDate) <= 0;
    }

}
