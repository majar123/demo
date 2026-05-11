package com.demo.api.dto;

import com.demo.entity.PetEntity.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Pet data transfer object")
public class PetDto {

    @Schema(description = "Unique identifier of the pet", example = "1")
    public Long id;

    @Schema(description = "Name of the pet", example = "Buddy")
    @NotBlank
    public String name;

    @Schema(description = "Birth date of the pet", example = "2020-03-15")
    public LocalDate birthDate;

    @Schema(description = "Type of the pet", example = "DOG")
    @NotNull
    public PetType type;

    @Schema(description = "ID of the owner", example = "1")
    @NotNull
    public Long ownerId;
}
