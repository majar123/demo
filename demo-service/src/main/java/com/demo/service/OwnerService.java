package com.demo.service;

import com.demo.entity.OwnerEntity;
import com.demo.service.exception.OwnerNotFoundException;
import com.demo.repository.OwnerRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OwnerService {

    @Inject
    OwnerRepository ownerRepository;

    public List<OwnerEntity> findAll() {
        Log.debug("Fetching all owners");
        return ownerRepository.getAll();
    }

    public OwnerEntity findById(Long id) {
        Log.debugf("Fetching owner with id=%d", id);
        return ownerRepository.getById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));
    }

    @Transactional
    public OwnerEntity create(OwnerEntity ownerEntity) {
        Log.infof("Creating owner name=%s %s", ownerEntity.firstName, ownerEntity.lastName);
        return ownerRepository.save(ownerEntity);
    }

    @Transactional
    public OwnerEntity update(Long id, OwnerEntity updated) {
        Log.infof("Updating owner id=%d", id);
        OwnerEntity ownerEntity = findById(id);
        ownerEntity.firstName = updated.firstName;
        ownerEntity.lastName = updated.lastName;
        ownerEntity.phone = updated.phone;
        ownerEntity.address = updated.address;
        return ownerEntity;
    }

    @Transactional
    public void delete(Long id) {
        Log.infof("Deleting owner id=%d", id);
        OwnerEntity ownerEntity = findById(id);
        ownerRepository.delete(ownerEntity);
    }
}
