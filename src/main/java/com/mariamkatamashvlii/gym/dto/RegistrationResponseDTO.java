package com.mariamkatamashvlii.gym.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class RegistrationResponseDTO {

    @Valid
    private String username;

    @Valid
    private String password;
}
