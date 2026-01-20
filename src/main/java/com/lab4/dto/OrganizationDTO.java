package com.lab4.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDTO {

    private Integer orgId;

    @NotBlank(message = "Organization name is required")
    @Size(min = 3, max = 255, message = "Organization name must be between 3 and 255 characters")
    private String orgName;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Pattern(regexp = "^$|^[0-9]{9,12}$", message = "Phone must contain only digits and be 9-12 characters long")
    private String phone;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
}
