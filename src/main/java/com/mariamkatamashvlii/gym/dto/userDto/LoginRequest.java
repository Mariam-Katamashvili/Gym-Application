package com.mariamkatamashvlii.gym.dto.userDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class LoginRequest {
    @Valid
    @NotNull
    private String username;

    @Valid
    @NotNull
    private String password;
}
