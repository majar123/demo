package com.demo.repository.impl;

import com.demo.entity.Pet;
import com.demo.repository.PetRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PetRepositoryImpl implements PetRepository, PanacheRepository<Pet> {

    @Inject
    EntityManager entityManager;

    @Override
    public List<Pet> getAll() {
        return listAll();
    }

    @Override
    public Optional<Pet> getById(Long id) {
        return findByIdOptional(id);
    }

    @Override
    public List<Pet> getByOwnerId(Long ownerId) {
        return list("owner.id", ownerId);
    }

    @Override
    @Transactional
    public Pet save(Pet pet) {
        if (pet.id == null) {
            persist(pet);
            return pet;
        }
        return entityManager.merge(pet);
    }

    @Override
    @Transactional
    public void delete(Pet pet) {
        deleteById(pet.id);
    }
}
