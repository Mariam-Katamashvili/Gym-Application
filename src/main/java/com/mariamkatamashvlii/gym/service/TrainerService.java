package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;

import java.sql.Date;
import java.util.List;

public interface TrainerService {
    Trainer create(Trainer trainer);

    Trainer update(Trainer trainer);

    Trainer select(String username);

    boolean changePassword(String username, String currPassword, String newPassword);

    void activateTrainer (String username, boolean isActive);

    void deactivateTrainer (String username, boolean isActive);

    Trainer createTrainerProfile(long trainingTypeId, long userId);

    List<Training> getTrainings(String username, String password, Date fromDate,
                                Date toDate, String traineeName);

    List<Trainer> findAll();
}
