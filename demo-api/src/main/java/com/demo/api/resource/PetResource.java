package com.demo.api.resource;

import com.demo.api.dto.request.PetRequest;
import com.demo.api.dto.response.PetResponse;
import com.demo.api.mapper.PetMapper;
import com.demo.entity.Owner;
import com.demo.service.OwnerService;
import com.demo.service.PetService;
import jakarta.inject.Inject;
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

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Pets", description = "Operations for managing pets")
public class PetResource {

    @Inject
    PetService petService;

    @Inject
    OwnerService ownerService;

    @Inject
    PetMapper petMapper;

    @GET
    @Operation(summary = "Get all pets", description = "Returns all pets")
    @APIResponse(responseCode = "200", description = "List of pets returned",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PetResponse.class)))
    public Response getAll() {
        return Response.ok(petService.findAll().stream().map(petMapper::toResponse).toList()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get pet by ID", description = "Returns a single pet by its ID")
    @APIResponse(responseCode = "200", description = "Pet found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PetResponse.class)))
    @APIResponse(responseCode = "404", description = "Pet not found")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(petMapper.toResponse(petService.findById(id))).build();
    }

    @GET
    @Path("/owner/{ownerId}")
    @Operation(summary = "Get pets by owner", description = "Returns all pets belonging to a specific owner")
    @APIResponse(responseCode = "200", description = "List of pets returned",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PetResponse.class)))
    @APIResponse(responseCode = "404", description = "Owner not found")
    public Response getByOwner(@PathParam("ownerId") Long ownerId) {
        return Response.ok(petService.findByOwner(ownerId).stream().map(petMapper::toResponse).toList()).build();
    }

    @POST
    @Operation(summary = "Create pet", description = "Creates a new pet for an existing owner")
    @APIResponse(responseCode = "201", description = "Pet created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PetResponse.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    @APIResponse(responseCode = "404", description = "Owner not found")
    public Response create(@Valid PetRequest request) {
        Owner owner = ownerService.findById(request.ownerId);
        PetResponse response = petMapper.toResponse(petService.create(petMapper.toEntity(request, owner)));
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update pet", description = "Updates an existing pet by its ID")
    @APIResponse(responseCode = "200", description = "Pet updated",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PetResponse.class)))
    @APIResponse(responseCode = "404", description = "Pet or owner not found")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response update(@PathParam("id") Long id, @Valid PetRequest request) {
        Owner owner = ownerService.findById(request.ownerId);
        return Response.ok(petMapper.toResponse(petService.update(id, petMapper.toEntity(request, owner)))).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete pet", description = "Deletes a pet by its ID")
    @APIResponse(responseCode = "204", description = "Pet deleted")
    @APIResponse(responseCode = "404", description = "Pet not found")
    public Response delete(@PathParam("id") Long id) {
        petService.delete(id);
        return Response.noContent().build();
    }
}
