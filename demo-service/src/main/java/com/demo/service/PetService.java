package com.demo.service;

import com.demo.entity.PetEntity;
import com.demo.service.exception.PetNotFoundException;
import com.demo.repository.PetRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PetService {

    @Inject
    PetRepository petRepository;

    public List<PetEntity> findAll() {
        Log.debug("Fetching all pets");
        return petRepository.getAll();
    }

    public PetEntity findById(Long id) {
        Log.debugf("Fetching pet with id=%d", id);
        return petRepository.getById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
    }

    public List<PetEntity> findByOwner(Long ownerId) {
        Log.debugf("Fetching pets for ownerId=%d", ownerId);
        return petRepository.getByOwnerId(ownerId);
    }

    @Transactional
    public PetEntity create(PetEntity petEntity) {
        Log.infof("Creating pet name=%s", petEntity.name);
        return petRepository.save(petEntity);
    }

    @Transactional
    public PetEntity update(Long id, PetEntity updated) {
        Log.infof("Updating pet id=%d", id);

        PetEntity petEntity = findById(id);
        petEntity.name = updated.name;
        petEntity.birthDate = updated.birthDate;
        petEntity.type = updated.type;
        petEntity.owner = updated.owner;

        return petEntity;
    }

    @Transactional
    public void delete(Long id) {
        Log.infof("Deleting pet id=%d", id);
        PetEntity pet = findById(id);
        if (pet.owner != null && pet.owner.pets != null) {
            pet.owner.pets.remove(pet);
        }
        petRepository.delete(pet);
    }
}
