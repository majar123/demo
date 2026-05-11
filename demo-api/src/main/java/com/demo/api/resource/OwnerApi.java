package com.demo.api.resource;

import com.demo.api.dto.OwnerDto;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/owners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Owners", description = "Operations for managing owners")
public interface OwnerApi {

    @GET
    @Operation(summary = "Get all owners", description = "Returns all owners")
    @APIResponse(responseCode = "200", description = "List of owners returned",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = OwnerDto.class)))
    Response getAll();

    @GET
    @Path("/{id}")
    @Operation(summary = "Get owner by ID", description = "Returns a single owner by their ID")
    @APIResponse(responseCode = "200", description = "Owner found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = OwnerDto.class)))
    @APIResponse(responseCode = "404", description = "Owner not found")
    Response getById(@PathParam("id") Long id);

    @POST
    @Operation(summary = "Create owner", description = "Creates a new owner")
    @APIResponse(responseCode = "201", description = "Owner created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = OwnerDto.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    Response create(@Valid OwnerDto request);

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update owner", description = "Updates an existing owner by their ID")
    @APIResponse(responseCode = "200", description = "Owner updated",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = OwnerDto.class)))
    @APIResponse(responseCode = "404", description = "Owner not found")
    @APIResponse(responseCode = "400", description = "Invalid input")
    Response update(@PathParam("id") Long id, @Valid OwnerDto request);

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete owner", description = "Deletes an owner by their ID")
    @APIResponse(responseCode = "204", description = "Owner deleted")
    @APIResponse(responseCode = "404", description = "Owner not found")
    Response delete(@PathParam("id") Long id);
}
