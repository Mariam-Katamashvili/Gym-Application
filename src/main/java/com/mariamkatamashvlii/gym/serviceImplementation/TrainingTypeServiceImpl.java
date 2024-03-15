package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepo;

    @Override
    public TrainingType select(long id) {
        log.info("Selecting TrainingType with id {}", id);
        return trainingTypeRepo.select(id);
    }

    @Override
    public List<TrainingType> findAll() {
        log.info("Returning all TrainingTypes");
        return trainingTypeRepo.findAll();
    }
}
