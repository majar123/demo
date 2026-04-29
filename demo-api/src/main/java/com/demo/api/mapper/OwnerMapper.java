package com.demo.api.mapper;

import com.demo.api.dto.request.OwnerRequest;
import com.demo.api.dto.response.OwnerResponse;
import com.demo.entity.Owner;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OwnerMapper {

    @Inject
    PetMapper petMapper;

    public Owner toEntity(OwnerRequest request) {
        Owner owner = new Owner();
        owner.firstName = request.firstName;
        owner.lastName = request.lastName;
        owner.phone = request.phone;
        owner.address = request.address;
        return owner;
    }

    public OwnerResponse toResponse(Owner owner) {
        OwnerResponse response = new OwnerResponse();
        response.id = owner.id;
        response.firstName = owner.firstName;
        response.lastName = owner.lastName;
        response.phone = owner.phone;
        response.address = owner.address;
        response.pets = owner.pets.stream().map(petMapper::toResponse).toList();
        return response;
    }
}
