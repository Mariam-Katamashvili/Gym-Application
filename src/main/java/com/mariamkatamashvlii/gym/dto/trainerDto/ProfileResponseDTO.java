package com.mariamkatamashvlii.gym.dto.trainerDto;

import com.mariamkatamashvlii.gym.dto.traineeDto.TraineeDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class ProfileResponseDTO {
    @Valid
    private String firstName;

    @Valid
    private String lastName;

    @Valid
    private TrainingTypeDTO specialization;

    @Valid
    private Boolean isActive;

    @Valid
    private List<TraineeDTO> trainees;
}
