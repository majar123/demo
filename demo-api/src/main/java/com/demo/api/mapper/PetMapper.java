package com.demo.api.mapper;

import com.demo.api.dto.PetDto;
import com.demo.entity.OwnerEntity;
import com.demo.entity.PetEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PetMapper {

    public PetEntity toEntity(PetDto dto, OwnerEntity ownerEntity) {
        PetEntity petEntity = new PetEntity();
        petEntity.name = dto.name;
        petEntity.birthDate = dto.birthDate;
        petEntity.type = dto.type;
        petEntity.owner = ownerEntity;
        return petEntity;
    }

    public PetDto toDto(PetEntity petEntity) {
        PetDto dto = new PetDto();
        dto.id = petEntity.id;
        dto.name = petEntity.name;
        dto.birthDate = petEntity.birthDate;
        dto.type = petEntity.type;
        dto.ownerId = petEntity.owner.id;
        return dto;
    }
}
