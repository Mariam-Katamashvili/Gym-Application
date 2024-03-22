package com.mariamkatamashvlii.gym.dto.traineeDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class TraineeDTO {
    @Valid
    private String username;

    @Valid
    private String firstName;

    @Valid
    private String lastName;
}
