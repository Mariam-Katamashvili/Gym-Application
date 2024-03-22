package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.exception.TrainingTypeFetchException;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepo;

    @Override
    public List<TrainingTypeDTO> findAll() {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Starting findAll operation for TrainingType", transactionId);
        try {
            List<TrainingTypeDTO> result = trainingTypeRepo.findAll().stream()
                    .map(trainingType -> new TrainingTypeDTO(
                            trainingType.getId(),
                            trainingType.getTrainingTypeName()))
                    .toList();
            log.info("[{}] Successfully completed findAll operation for TrainingType", transactionId);
            return result;
        } catch (Exception e) {
            log.error("[{}] Error during findAll operation", e.getMessage());
            throw new TrainingTypeFetchException("Error retrieving all training types", e);
        }
    }
}
