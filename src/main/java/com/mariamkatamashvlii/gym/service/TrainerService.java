package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainerTrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;

import java.util.List;

public interface TrainerService {

    RegistrationResponseDTO registerTrainer(RegistrationRequestDTO registrationRequestDTO);

    ProfileResponseDTO getTrainerProfile(String username);

    UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO);

    List<TrainingResponseDTO> getTrainings(TrainerTrainingsRequestDTO trainerTrainingsRequestDTO);

    void toggleActivation(ToggleActivationDTO toggleActivationDTO);

}
