package com.demo.api.dto;

import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(description = "Owner data transfer object")
public class OwnerDto {

    @Schema(description = "Unique identifier of the owner", example = "1")
    public Long id;

    @Schema(description = "First name of the owner", example = "John")
    @NotBlank
    public String firstName;

    @Schema(description = "Last name of the owner", example = "Doe")
    @NotBlank
    public String lastName;

    @Schema(description = "Phone number", example = "555-1234")
    @NotBlank
    public String phone;

    @Schema(description = "Home address", example = "123 Main St")
    public String address;

    @Schema(description = "List of pets owned")
    public List<PetDto> pets;
}
