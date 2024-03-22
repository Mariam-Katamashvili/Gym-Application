package com.mariamkatamashvlii.gym.dto.traineeDto;

import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.sql.Date;
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
    private Date birthday;

    @Valid
    private String address;

    @Valid
    private boolean isActive;

    @Valid
    List<TrainerDTO> trainers;
}
