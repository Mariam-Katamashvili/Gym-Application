package com.mariamkatamashvlii.gym.dto.traineeDto;

import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerUsenameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class UpdateTrainersRequestDTO {
    private String username;
    List<TrainerUsenameDTO> trainers;
}
