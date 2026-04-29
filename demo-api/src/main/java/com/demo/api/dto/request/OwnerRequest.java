package com.demo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Request object for creating or updating an owner")
public class OwnerRequest {

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
}
