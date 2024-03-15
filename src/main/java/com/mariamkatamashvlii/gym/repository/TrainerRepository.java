package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Trainer;

import java.util.List;

public interface TrainerRepository {
    Trainer create(Trainer trainer);

    Trainer update(Trainer trainer);

    Trainer select(long id);

    Trainer select(String username);

    List<Trainer> findAll();
}
