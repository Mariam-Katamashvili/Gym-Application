package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.TrainingType;

import java.sql.Date;
import java.util.List;

public interface TraineeService {
    Trainee create(Trainee trainee);

    Trainee update(Trainee trainee);

    void delete(String username);

    Trainee select(String username);

    boolean changePassword(String username, String currPassword, String newPassword);

    void activateTrainee (String username, boolean isActive);

    void deactivateTrainee (String username, boolean isActive);

    void updateTrainers(String username, List<Trainer> trainers);

    Trainee createTraineeProfile(Date dob, String address, long userId);

    List<Training> getTrainings(String username, Date fromDate,
                                Date toDate, String trainerName, TrainingType trainingType);

    List<Trainer> getNotAssignedTrainers(String username);

    List<Trainee> findAll();
}