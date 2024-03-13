package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.TrainingType;

import java.util.List;

public interface TrainingTypeService {
    TrainingType select(long id);

    List<TrainingTypeDTO> findAll();
}
