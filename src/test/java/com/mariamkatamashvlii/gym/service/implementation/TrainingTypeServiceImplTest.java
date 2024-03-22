package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.exception.TrainingTypeFetchException;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {
    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    private TrainingType trainingType;
    private TrainingTypeDTO trainingTypeDTO;

    @BeforeEach
    void setUp() {
        trainingType = TrainingType.builder()
                .id(1L)
                .trainingTypeName("Yoga")
                .build();

        trainingTypeDTO = TrainingTypeDTO.builder()
                .trainingTypeId(1L)
                .trainingTypeName("Yoga")
                .build();
    }
    @Test
    void findAll_Success() {
        when(trainingTypeRepository.findAll()).thenReturn(List.of(trainingType));

        List<TrainingTypeDTO> result = trainingTypeService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(trainingTypeDTO.getTrainingTypeId(), result.get(0).getTrainingTypeId());
        assertEquals(trainingTypeDTO.getTrainingTypeName(), result.get(0).getTrainingTypeName());

        verify(trainingTypeRepository).findAll();
    }

    @Test
    void testFindAllThrowsException() {
        when(trainingTypeRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(TrainingTypeFetchException.class, () -> trainingTypeService.findAll());

        verify(trainingTypeRepository).findAll();
    }
}
