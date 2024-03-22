package com.mariamkatamashvlii.gym.dto.traineeDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class UpdateRequestDTO {
    @Valid
    @NotNull
    private String username;

    @Valid
    @NotNull
    private String firstName;

    @Valid
    @NotNull
    private String lastName;

    @Valid
    private Date birthday;

    @Valid
    private String address;

    @Valid
    @NotNull
    private Boolean isActive;
}
