package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Trainee;

import java.util.List;

public interface TraineeRepository {
    Trainee create(Trainee trainee);

    Trainee update(Trainee trainee);

    void delete(String username);

    Trainee select(long id);

    Trainee select(String username);

    List<Trainee> findAll();
}
