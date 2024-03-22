package com.mariamkatamashvlii.gym.dto.trainerDto;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class RegistrationRequestDTO {
    @Valid
    @NotNull
    private String firstName;

    @Valid
    @NotNull
    private String lastName;

    @Valid
    @NotNull
    private TrainingTypeDTO specialization;
}
