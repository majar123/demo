package com.demo.repository.impl;

import com.demo.entity.PetEntity;
import com.demo.repository.PetRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PetRepositoryImpl implements PetRepository, PanacheRepository<PetEntity> {

    @Override
    public List<PetEntity> getAll() {
        return listAll();
    }

    @Override
    public Optional<PetEntity> getById(Long id) {
        return findByIdOptional(id);
    }

    @Override
    public List<PetEntity> getByOwnerId(Long ownerId) {
        return list("ownerEntity.id", ownerId);
    }

    @Override
    public PetEntity save(PetEntity pet) {
        persist(pet);
        return pet;
    }

    @Override
    public void delete(PetEntity petEntity) {
        deleteById(petEntity.id);
    }
}
