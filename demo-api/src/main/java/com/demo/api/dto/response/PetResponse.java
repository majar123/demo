package com.demo.api.dto.response;

import com.demo.entity.Pet.PetType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Pet response object")
public class PetResponse {

    @Schema(description = "Unique identifier of the pet", example = "1")
    public Long id;

    @Schema(description = "Name of the pet", example = "Buddy")
    public String name;

    @Schema(description = "Birth date of the pet", example = "2020-03-15")
    public LocalDate birthDate;

    @Schema(description = "Type of the pet", example = "DOG")
    public PetType type;

    @Schema(description = "ID of the owner", example = "1")
    public Long ownerId;
}
