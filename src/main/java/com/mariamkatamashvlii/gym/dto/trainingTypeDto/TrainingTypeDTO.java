package com.mariamkatamashvlii.gym.dto.trainingTypeDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class TrainingTypeDTO {
    @Valid
    private Long trainingTypeId;

    @Valid
    private String trainingTypeName;
}
