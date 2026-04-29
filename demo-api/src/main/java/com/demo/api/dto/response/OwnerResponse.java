package com.demo.api.dto.response;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(description = "Owner response object")
public class OwnerResponse {

    @Schema(description = "Unique identifier of the owner", example = "1")
    public Long id;

    @Schema(description = "First name of the owner", example = "John")
    public String firstName;

    @Schema(description = "Last name of the owner", example = "Doe")
    public String lastName;

    @Schema(description = "Phone number", example = "555-1234")
    public String phone;

    @Schema(description = "Home address", example = "123 Main St")
    public String address;

    @Schema(description = "List of pets owned")
    public List<PetResponse> pets;
}
