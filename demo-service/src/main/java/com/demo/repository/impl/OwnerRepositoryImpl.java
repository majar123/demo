package com.demo.repository.impl;

import com.demo.entity.Owner;
import com.demo.repository.OwnerRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OwnerRepositoryImpl implements OwnerRepository, PanacheRepository<Owner> {

    @Inject
    EntityManager entityManager;

    @Override
    public List<Owner> getAll() {
        return listAll();
    }

    @Override
    public Optional<Owner> getById(Long id) {
        return findByIdOptional(id);
    }

    @Override
    @Transactional
    public Owner save(Owner owner) {
        if (owner.id == null) {
            persist(owner);
            return owner;
        }
        return entityManager.merge(owner);
    }

    @Override
    @Transactional
    public void delete(Owner owner) {
        deleteById(owner.id);
    }
}
