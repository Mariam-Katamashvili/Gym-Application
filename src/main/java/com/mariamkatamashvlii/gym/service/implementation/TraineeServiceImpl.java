package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerUsenameDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TraineeTrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.UserNotCreatedException;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepo;
    private final UserRepository userRepo;
    private final TrainerRepository trainerRepo;
    private final TrainingRepository trainingRepository;
    private final UsernameGenerator usernameGenerator;
    private final PasswordGenerator passwordGenerator;

    @Override
    @Transactional
    public RegistrationResponseDTO registerTrainee(RegistrationRequestDTO registrationRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Attempting to register a new trainee", transactionId);
        try {
            String firstName = registrationRequestDTO.getFirstName();
            String lastName = registrationRequestDTO.getLastName();
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(usernameGenerator.generateUsername(firstName, lastName))
                    .password(passwordGenerator.generatePassword())
                    .isActive(true)
                    .build();
            userRepo.save(user);

            Trainee trainee = Trainee.builder()
                    .birthday(registrationRequestDTO.getBirthday())
                    .address(registrationRequestDTO.getAddress())
                    .user(user)
                    .build();
            traineeRepo.save(trainee);
            log.info("[{}] Successfully registered new trainee with username: {}", transactionId, user.getUsername());
            return new RegistrationResponseDTO(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            log.error("[{}] Failed to register trainee: {}", transactionId, e.getMessage(), e);
            throw new UserNotCreatedException("Could not create trainee due to an error: " + e.getMessage());
        }
    }

    @Override
    public ProfileResponseDTO getTraineeProfile(String username) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Fetching profile for trainee: {}", transactionId, username);

        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("[{}] User not found with username: {}", transactionId, username);
            throw new EntityNotFoundException("User not found with username: " + username);
        }

        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            log.error("[{}] Trainee not found with username: {}", transactionId, username);
            throw new EntityNotFoundException("Trainee not found with username: " + username);
        }

        List<TrainerDTO> trainers = trainee.getTrainers().stream().map(trainer -> {
            TrainerDTO dto = new TrainerDTO();
            dto.setUsername(trainer.getUser().getUsername());
            dto.setFirstName(trainer.getUser().getFirstName());
            dto.setLastName(trainer.getUser().getLastName());
            TrainingTypeDTO specializationDTO = new TrainingTypeDTO(
                    trainer.getSpecialization().getId(),
                    trainer.getSpecialization().getTrainingTypeName()
            );
            dto.setSpecialization(specializationDTO);
            return dto;
        }).toList();

        log.info("[{}] Successfully fetched profile for trainee: {}", transactionId, username);
        return new ProfileResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                trainee.getBirthday(),
                trainee.getAddress(),
                user.getIsActive(),
                trainers
        );
    }

    @Override
    @Transactional
    public UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Starting profile update for username: {}", transactionId, updateRequestDTO.getUsername());

        User user = userRepo.findByUsername(updateRequestDTO.getUsername());
        if (user == null) {
            log.error("[{}] User not found with username: {}", transactionId, updateRequestDTO.getUsername());
            throw new EntityNotFoundException("User not found with username: " + updateRequestDTO.getUsername());
        }
        user.setFirstName(updateRequestDTO.getFirstName());
        user.setLastName(updateRequestDTO.getLastName());
        user.setIsActive(updateRequestDTO.getIsActive());
        userRepo.save(user);

        Trainee trainee = traineeRepo.findByUsername(updateRequestDTO.getUsername());
        if (trainee == null) {
            log.error("[{}] Trainee not found with username: {}", transactionId, updateRequestDTO.getUsername());
            throw new EntityNotFoundException("Trainee not found with username: " + updateRequestDTO.getUsername());
        }
        trainee.setBirthday(updateRequestDTO.getBirthday());
        trainee.setAddress(updateRequestDTO.getAddress());
        traineeRepo.save(trainee);

        List<TrainerDTO> trainers = trainee.getTrainers().stream().map(trainer -> {
            TrainerDTO dto = new TrainerDTO();
            dto.setUsername(trainer.getUser().getUsername());
            dto.setFirstName(trainer.getUser().getFirstName());
            dto.setLastName(trainer.getUser().getLastName());
            TrainingTypeDTO specializationDTO = new TrainingTypeDTO(
                    trainer.getSpecialization().getId(),
                    trainer.getSpecialization().getTrainingTypeName()
            );
            dto.setSpecialization(specializationDTO);
            return dto;
        }).toList();

        log.info("[{}] Profile successfully updated for username: {}", transactionId, user.getUsername());
        return new UpdateResponseDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                trainee.getBirthday(),
                trainee.getAddress(),
                user.getIsActive(),
                trainers
        );
    }

    @Override
    @Transactional
    public void delete(String username) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Initiating deletion for username: {}", transactionId, username);

        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            log.error("[{}] Trainee does not exist for username: {}", transactionId, username);
            throw new EntityNotFoundException("Trainee does not exist for username: " + username);
        }

        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("[{}] User does not exist for username: {}", transactionId, username);
            throw new EntityNotFoundException("User does not exist for username: " + username);
        }
        Set<Training> trainings = trainee.getTrainings();
        for (Training t : trainings) {
            trainingRepository.delete(t);
            log.debug("[{}] Deleted training with ID: {} for username: {}", transactionId, t.getId(), username);
        }
        traineeRepo.delete(trainee);
        log.info("[{}] Deleted trainee with username: {}", transactionId, username);

        userRepo.delete(user);
        log.info("[{}] Deleted user with username: {}", transactionId, username);
    }

    @Override
    public List<TrainerDTO> getUnassignedTrainers(String username) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Fetching unassigned trainers for trainee: {}", transactionId, username);

        List<TrainerDTO> unassignedTrainers = new ArrayList<>();
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            log.warn("[{}] Trainee with username {} not found, returning empty trainer list.", transactionId, username);
            return unassignedTrainers;
        }

        List<Trainer> traineeTrainers = trainee.getTrainers();
        List<Trainer> allTrainers = trainerRepo.findAll();
        for (Trainer t : allTrainers) {
            if (!traineeTrainers.contains(t)) {
                TrainerDTO dto = new TrainerDTO();
                dto.setUsername(t.getUser().getUsername());
                dto.setFirstName(t.getUser().getFirstName());
                dto.setLastName(t.getUser().getLastName());
                TrainingTypeDTO specializationDTO = new TrainingTypeDTO(
                        t.getSpecialization().getId(),
                        t.getSpecialization().getTrainingTypeName()
                );
                dto.setSpecialization(specializationDTO);
                unassignedTrainers.add(dto);
            }
        }

        log.info("[{}] Successfully fetched unassigned trainers for trainee: {}. Number of trainers found: {}", transactionId, username, unassignedTrainers.size());
        return unassignedTrainers;
    }

    @Override
    public List<TrainingResponseDTO> getTrainings(TraineeTrainingsRequestDTO traineeTrainingsRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = traineeTrainingsRequestDTO.getUsername();
        String trainerName = traineeTrainingsRequestDTO.getTrainerName();
        LocalDate fromDate = traineeTrainingsRequestDTO.getFromDate();
        LocalDate toDate = traineeTrainingsRequestDTO.getToDate();
        TrainingTypeDTO trainingType = traineeTrainingsRequestDTO.getTrainingType();
        log.info("[{}] Fetched trainings for trainee: {} with criteria - TrainerName: {}, FromDate: {}, ToDate: {}, TrainingType: {}",
                transactionId, username, trainerName, fromDate,
                toDate, trainingType);

        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null || trainee.getTrainings() == null) {
            log.warn("[{}] No trainings found or trainee does not exist for username: {}", transactionId, username);
            throw new EntityNotFoundException("Trainee does not exist or has no trainings for username: " + username);
        }

        log.info("[{}] Successfully fetched trainings for trainee: {}", transactionId, username);
        return trainee.getTrainings().stream()
                .filter(t -> (fromDate == null || !t.getTrainingDate().isBefore(fromDate)) && (toDate == null || !t.getTrainingDate().isAfter(toDate)))
                .filter(t -> trainerName == null || t.getTrainer().getUser().getUsername().equalsIgnoreCase(trainerName))
                .filter(t -> trainingType == null || t.getTrainingType().getTrainingTypeName().equals(trainingType.getTrainingTypeName()))
                .map(t -> {
                    TrainingResponseDTO dto = new TrainingResponseDTO();
                    dto.setTrainingName(t.getTrainingName());
                    dto.setDate(t.getTrainingDate());
                    dto.setTrainingType(t.getTrainingType());
                    dto.setDuration(t.getDuration());
                    dto.setName(t.getTrainer().getUser().getUsername());
                    return dto;
                }).toList();
    }

    @Override
    public List<TrainerDTO> updateTrainers(UpdateTrainersRequestDTO updateTrainersRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = updateTrainersRequestDTO.getUsername();
        log.info("[{}] Starting to update trainers for trainee: {}", transactionId, username);

        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            log.error("[{}] Trainee not found with username: {}", transactionId, username);
            throw new EntityNotFoundException("Trainee not found with username: " + username);
        }

        List<TrainerDTO> newTrainers = new ArrayList<>();
        List<Trainer> updatedTrainers = updateTrainersRequestDTO.getTrainers().stream()
                .map(TrainerUsenameDTO::getUsername)
                .map(trainerRepo::findByUsername)
                .toList();
        trainee.setTrainers(updatedTrainers);
        traineeRepo.save(trainee);
        log.info("Updated trainer list for trainee - {}", username);
        updatedTrainers.forEach(trainer -> {
            TrainerDTO dto = new TrainerDTO();
            dto.setUsername(trainer.getUser().getUsername());
            dto.setFirstName(trainer.getUser().getFirstName());
            dto.setLastName(trainer.getUser().getLastName());
            TrainingTypeDTO specializationDTO = new TrainingTypeDTO(
                    trainer.getSpecialization().getId(),
                    trainer.getSpecialization().getTrainingTypeName()
            );
            dto.setSpecialization(specializationDTO);
            newTrainers.add(dto);
        });

        log.info("[{}] Successfully updated trainer list for trainee: {}", transactionId, username);
        return newTrainers;
    }

    @Override
    public void toggleActivation(ToggleActivationDTO toggleActivationDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = toggleActivationDTO.getUsername();
        log.info("[{}] Attempting to toggle activation for user: {}", transactionId, username);

        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("[{}] User not found with username: {}", transactionId, username);
            throw new EntityNotFoundException("User not found with username: " + username);
        }

        user.setIsActive(toggleActivationDTO.getIsActive());
        userRepo.save(user);
        log.info("[{}] Successfully toggled activation for user: {}. Now active: {}", transactionId, username, user.getIsActive());
    }
}
