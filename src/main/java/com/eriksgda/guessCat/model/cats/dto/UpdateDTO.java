package com.eriksgda.guessCat.model.cats.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateDTO(

        @NotBlank(message = "Username cannot be blank.")
        @Size(min = 3, max = 80, message = "Username must be between 3 and 80 characters.")
        String newUsername,

        @NotBlank(message = "Password cannot be blank.")
        @Size(min = 3, max = 80, message = "Password must be between 3 and 80 characters.")
        String newPassword
) {
}
