package com.demo.repository;

import com.demo.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    List<Pet> getAll();

    Optional<Pet> getById(Long id);

    List<Pet> getByOwnerId(Long ownerId);

    Pet save(Pet pet);

    void delete(Pet pet);
}
