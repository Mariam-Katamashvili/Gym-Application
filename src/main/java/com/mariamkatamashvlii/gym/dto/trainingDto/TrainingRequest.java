package com.mariamkatamashvlii.gym.dto.trainingDto;

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
public class TrainingRequest {
    @Valid
    @NotNull
    private String traineeUsername;

    @Valid
    @NotNull
    private String trainerUsername;

    @Valid
    @NotNull
    private String trainingName;

    @Valid
    @NotNull
    private LocalDate date;

    @Valid
    @NotNull
    private Number duration;
}
