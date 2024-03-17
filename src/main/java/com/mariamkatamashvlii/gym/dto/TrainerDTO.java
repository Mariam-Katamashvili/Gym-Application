package com.mariamkatamashvlii.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class TrainerDTO {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingTypeDTO specialization;
}