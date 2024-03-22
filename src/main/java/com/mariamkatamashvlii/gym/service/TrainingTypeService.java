package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;

import java.util.List;

public interface TrainingTypeService {
    List<TrainingTypeDTO> findAll();
}
