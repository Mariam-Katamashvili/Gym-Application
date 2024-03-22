package com.mariamkatamashvlii.gym.controller;

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
import com.mariamkatamashvlii.gym.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/traineeProfile")
public class TraineeController {
    private final TraineeService traineeService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> registration(
            @RequestBody RegistrationRequestDTO registrationRequestDTO) {
        RegistrationResponseDTO registrationResponseDTO = traineeService.registerTrainee(registrationRequestDTO);
        return ResponseEntity.ok(registrationResponseDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponseDTO> getProfile(
            @PathVariable String username) {
        ProfileResponseDTO profile = traineeService.getTraineeProfile(username);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateResponseDTO> update(
            @RequestBody UpdateRequestDTO updateRequestDTO) {
        UpdateResponseDTO response = traineeService.updateProfile(updateRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<String> delete(
            @PathVariable String username) {
        traineeService.delete(username);
        return ResponseEntity.ok("Trainee deleted successfully");
    }

    @GetMapping("/{username}/unassigned")
    public ResponseEntity<List<TrainerDTO>> getUnassigned(
            @PathVariable String username) {
        List<TrainerDTO> trainerDTO = traineeService.getUnassignedTrainers(username);
        return ResponseEntity.ok(trainerDTO);
    }
    @PutMapping("/{username}/updateTrainers")
    public ResponseEntity<List<TrainerDTO>> updateTrainers(
            @RequestBody UpdateTrainersRequestDTO updateTrainersRequestDTO) {
        List<TrainerDTO> trainerDTO = traineeService.updateTrainers(updateTrainersRequestDTO);
        return ResponseEntity.ok(trainerDTO);
    }
    @GetMapping("/{username}/getTrainings")
    public ResponseEntity<List<TrainingResponseDTO>> traineeTrainings(
            @RequestBody TraineeTrainingsRequestDTO traineeTrainingsRequestDTO) {
        List<TrainingResponseDTO> trainingResponseDTO = traineeService.getTrainings(traineeTrainingsRequestDTO);
        return ResponseEntity.ok(trainingResponseDTO);
    }

    @PatchMapping("/{username}/toggleActivation")
    public ResponseEntity<String> toggleActivation(
            @RequestBody ToggleActivationDTO toggleActivationDTO) {
        traineeService.toggleActivation(toggleActivationDTO);
        return ResponseEntity.ok("Activation status changed");
    }
}