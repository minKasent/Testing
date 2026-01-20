package com.lab4.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectorDTO {

    private Integer directorId;

    @NotBlank(message = "Director name is required")
    @Size(min = 2, max = 255, message = "Director name must be between 2 and 255 characters")
    private String directorName;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Pattern(regexp = "^$|^[0-9]{9,12}$", message = "Phone must contain only digits and be 9-12 characters long")
    private String phone;

    private Integer orgId;
}
