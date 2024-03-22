package com.mariamkatamashvlii.gym.dto.trainingDto;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class TraineeTrainingsRequestDTO {
    @Valid
    @NotNull
    private String username;

    @Valid
    private LocalDate fromDate;

    @Valid
    private LocalDate toDate;

    @Valid
    private String trainerName;

    @Valid
    private TrainingTypeDTO trainingType;
}
