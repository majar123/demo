package com.demo.api.mapper;

import com.demo.api.dto.request.PetRequest;
import com.demo.api.dto.response.PetResponse;
import com.demo.entity.Owner;
import com.demo.entity.Pet;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PetMapper {

    public Pet toEntity(PetRequest request, Owner owner) {
        Pet pet = new Pet();
        pet.name = request.name;
        pet.birthDate = request.birthDate;
        pet.type = request.type;
        pet.owner = owner;
        return pet;
    }

    public PetResponse toResponse(Pet pet) {
        PetResponse response = new PetResponse();
        response.id = pet.id;
        response.name = pet.name;
        response.birthDate = pet.birthDate;
        response.type = pet.type;
        response.ownerId = pet.owner.id;
        return response;
    }
}
