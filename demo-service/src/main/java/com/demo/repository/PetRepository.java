package com.demo.repository;

import com.demo.entity.PetEntity;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    List<PetEntity> getAll();

    Optional<PetEntity> getById(Long id);

    List<PetEntity> getByOwnerId(Long ownerId);

    PetEntity save(PetEntity petEntity);

    void delete(PetEntity petEntity);
}
