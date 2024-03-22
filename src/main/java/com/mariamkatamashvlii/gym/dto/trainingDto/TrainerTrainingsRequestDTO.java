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
public class TrainerTrainingsRequestDTO {
    @Valid
    @NotNull
    private String username;

    @Valid
    private LocalDate fromDate;

    @Valid
    private LocalDate toDate;

    @Valid
    private String traineeName;

}
