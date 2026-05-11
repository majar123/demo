package com.demo.api.mapper;

import com.demo.api.dto.OwnerDto;
import com.demo.entity.OwnerEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OwnerMapper {

    @Inject
    PetMapper petMapper;

    public OwnerEntity toEntity(OwnerDto dto) {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.firstName = dto.firstName;
        ownerEntity.lastName = dto.lastName;
        ownerEntity.phone = dto.phone;
        ownerEntity.address = dto.address;
        return ownerEntity;
    }

    public OwnerDto toDto(OwnerEntity ownerEntity) {
        OwnerDto dto = new OwnerDto();
        dto.id = ownerEntity.id;
        dto.firstName = ownerEntity.firstName;
        dto.lastName = ownerEntity.lastName;
        dto.phone = ownerEntity.phone;
        dto.address = ownerEntity.address;
        dto.pets = ownerEntity.petEntities.stream().map(petMapper::toDto).toList();
        return dto;
    }
}
