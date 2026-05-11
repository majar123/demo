package com.demo.api.resource.impl;

import com.demo.api.dto.OwnerDto;
import com.demo.api.mapper.OwnerMapper;
import com.demo.api.resource.OwnerApi;
import com.demo.service.OwnerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

public class OwnerResource implements OwnerApi {

    @Inject
    OwnerService ownerService;

    @Inject
    OwnerMapper ownerMapper;

    @Override
    public Response getAll() {
        return Response.ok(ownerService.findAll().stream().map(ownerMapper::toDto).toList()).build();
    }

    @Override
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ownerMapper.toDto(ownerService.findById(id))).build();
    }

    @Override
    public Response create(@Valid OwnerDto request) {
        OwnerDto response = ownerMapper.toDto(ownerService.create(ownerMapper.toEntity(request)));
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response update(@PathParam("id") Long id, @Valid OwnerDto request) {
        return Response.ok(ownerMapper.toDto(ownerService.update(id, ownerMapper.toEntity(request)))).build();
    }

    @Override
    public Response delete(@PathParam("id") Long id) {
        ownerService.delete(id);
        return Response.noContent().build();
    }
}
