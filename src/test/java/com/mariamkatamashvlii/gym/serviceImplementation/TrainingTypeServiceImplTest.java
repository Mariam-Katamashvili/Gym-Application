package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingTypeServiceImplTest {
    @Mock
    private TrainingTypeRepository trainingTypeRepo;
    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelect() {
        long id = 1L;
        TrainingType expectedTrainingType = new TrainingType();
        expectedTrainingType.setTrainingTypeId(id);

        when(trainingTypeRepo.select(id)).thenReturn(expectedTrainingType);

        TrainingType actualTrainingType = trainingTypeService.select(id);

        assertEquals(expectedTrainingType, actualTrainingType);
        verify(trainingTypeRepo).select(id);
    }

    @Test
    void testFindAll() {
        List<TrainingType> expectedTrainingTypes = Arrays.asList(new TrainingType(), new TrainingType());

        when(trainingTypeRepo.findAll()).thenReturn(expectedTrainingTypes);

        List<TrainingType> actualTrainingTypes = trainingTypeService.findAll();

        assertEquals(expectedTrainingTypes, actualTrainingTypes);
        verify(trainingTypeRepo).findAll();
    }
}
