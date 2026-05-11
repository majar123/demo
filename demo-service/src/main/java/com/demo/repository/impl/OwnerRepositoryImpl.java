package com.demo.repository.impl;

import com.demo.entity.OwnerEntity;
import com.demo.repository.OwnerRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OwnerRepositoryImpl implements OwnerRepository, PanacheRepository<OwnerEntity> {

    @Override
    public List<OwnerEntity> getAll() {
        return listAll();
    }

    @Override
    public Optional<OwnerEntity> getById(Long id) {
        return findByIdOptional(id);
    }

    @Override
    public OwnerEntity save(OwnerEntity ownerEntity) {
        persist(ownerEntity);
        return ownerEntity;
    }

    @Override
    public void delete(OwnerEntity ownerEntity) {
        deleteById(ownerEntity.id);
    }
}
