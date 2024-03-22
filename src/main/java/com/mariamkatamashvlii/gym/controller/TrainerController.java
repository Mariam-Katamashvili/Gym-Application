package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainerTrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/trainerProfile")
public class TrainerController {
    private final TrainerService trainerService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> registration(
            @RequestBody RegistrationRequestDTO registrationRequestDTO) {
        RegistrationResponseDTO registrationDTO = trainerService.registerTrainer(registrationRequestDTO);
        return ResponseEntity.ok(registrationDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponseDTO> getProfile(
            @PathVariable String username) {
        ProfileResponseDTO response = trainerService.getTrainerProfile(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateResponseDTO> update(
            @RequestBody UpdateRequestDTO updateRequestDTO) {
        UpdateResponseDTO updateResponseDTO = trainerService.updateProfile(updateRequestDTO);
        return ResponseEntity.ok(updateResponseDTO);
    }

    @GetMapping("/{username}/getTrainings")
    public ResponseEntity<List<TrainingResponseDTO>> trainerTrainings(
            @RequestBody TrainerTrainingsRequestDTO trainerTrainingsRequestDTO) {
        List<TrainingResponseDTO> trainingResponseDTO = trainerService.getTrainings(trainerTrainingsRequestDTO);
        return ResponseEntity.ok(trainingResponseDTO);
    }

    @PatchMapping("/{username}/toggleActivation")
    public ResponseEntity<String> toggleActivation(
            @RequestBody ToggleActivationDTO toggleActivationDTO) {
        trainerService.toggleActivation(toggleActivationDTO);
        return ResponseEntity.ok("Activation status changed");
    }
}
