package com.mariamkatamashvlii.gym.dto.trainerDto;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class TrainerDTO {
    @Valid
    private String username;

    @Valid
    private String firstName;

    @Valid
    private String lastName;

    @Valid
    private TrainingTypeDTO specialization;
}
