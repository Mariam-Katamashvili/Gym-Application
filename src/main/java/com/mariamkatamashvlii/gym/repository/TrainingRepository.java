package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Training;

import java.util.List;

public interface TrainingRepository {
    Training create(Training training);

    Training update(Training training);

    void delete(long id);

    Training select(long id);

    List<Training> findAll();
}
