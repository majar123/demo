package com.demo.api.resource.impl;

import com.demo.api.dto.PetDto;
import com.demo.api.mapper.PetMapper;
import com.demo.api.resource.PetApi;
import com.demo.entity.OwnerEntity;
import com.demo.service.OwnerService;
import com.demo.service.PetService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

public class PetResource implements PetApi {

    @Inject
    PetService petService;

    @Inject
    OwnerService ownerService;

    @Inject
    PetMapper petMapper;

    @Override
    public Response getAll() {
        return Response.ok(petService.findAll().stream().map(petMapper::toDto).toList()).build();
    }

    @Override
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(petMapper.toDto(petService.findById(id))).build();
    }

    @Override
    public Response getByOwner(@PathParam("ownerId") Long ownerId) {
        return Response.ok(petService.findByOwner(ownerId).stream().map(petMapper::toDto).toList()).build();
    }

    @Override
    public Response create(@Valid PetDto request) {
        OwnerEntity ownerEntity = ownerService.findById(request.ownerId);
        PetDto response = petMapper.toDto(petService.create(petMapper.toEntity(request, ownerEntity)));
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response update(@PathParam("id") Long id, @Valid PetDto request) {
        OwnerEntity ownerEntity = ownerService.findById(request.ownerId);
        return Response.ok(petMapper.toDto(petService.update(id, petMapper.toEntity(request, ownerEntity)))).build();
    }

    @Override
    public Response delete(@PathParam("id") Long id) {
        petService.delete(id);
        return Response.noContent().build();
    }
}
