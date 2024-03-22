package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TraineeTrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;

import java.util.List;

public interface TraineeService {
    RegistrationResponseDTO registerTrainee(RegistrationRequestDTO registrationRequestDTO);
    ProfileResponseDTO getTraineeProfile(String username);

    UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO);

    void delete(String username);

    List<TrainerDTO> getUnassignedTrainers(String username);

    List<TrainingResponseDTO> getTrainings(TraineeTrainingsRequestDTO traineeTrainingsRequestDTO);

    List<TrainerDTO> updateTrainers(UpdateTrainersRequestDTO updateTrainersRequestDTO);

    void toggleActivation(ToggleActivationDTO toggleActivationDTO);

}