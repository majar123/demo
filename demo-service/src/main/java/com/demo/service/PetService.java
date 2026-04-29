package com.demo.service;

import com.demo.entity.Pet;
import com.demo.service.exception.PetNotFoundException;
import com.demo.repository.PetRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PetService {

    @Inject
    PetRepository petRepository;

    public List<Pet> findAll() {
        Log.debug("Fetching all pets");
        return petRepository.getAll();
    }

    public Pet findById(Long id) {
        Log.debugf("Fetching pet with id=%d", id);
        return petRepository.getById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
    }

    public List<Pet> findByOwner(Long ownerId) {
        Log.debugf("Fetching pets for ownerId=%d", ownerId);
        return petRepository.getByOwnerId(ownerId);
    }

    public Pet create(Pet pet) {
        Log.infof("Creating pet name=%s", pet.name);
        return petRepository.save(pet);
    }

    public Pet update(Long id, Pet updated) {
        Log.infof("Updating pet id=%d", id);
        Pet pet = petRepository.getById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        pet.name = updated.name;
        pet.birthDate = updated.birthDate;
        pet.type = updated.type;
        pet.owner = updated.owner;
        return petRepository.save(pet);
    }

    public void delete(Long id) {
        Log.infof("Deleting pet id=%d", id);
        Pet pet = petRepository.getById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        petRepository.delete(pet);
    }
}
