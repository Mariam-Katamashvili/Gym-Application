package com.mariamkatamashvlii.gym.dto.trainingDto;

import com.mariamkatamashvlii.gym.entity.TrainingType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class TrainingResponseDTO {
    @Valid
    private String trainingName;

    @Valid
    private LocalDate date;

    @Valid
    private TrainingType trainingType;

    @Valid
    private Number duration;

    @Valid
    private String name;
}
