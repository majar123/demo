package com.demo.service;

import com.demo.entity.Owner;
import com.demo.service.exception.OwnerNotFoundException;
import com.demo.repository.OwnerRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class OwnerService {

    @Inject
    OwnerRepository ownerRepository;

    public List<Owner> findAll() {
        Log.debug("Fetching all owners");
        return ownerRepository.getAll();
    }

    public Owner findById(Long id) {
        Log.debugf("Fetching owner with id=%d", id);
        return ownerRepository.getById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));
    }

    public Owner create(Owner owner) {
        Log.infof("Creating owner name=%s %s", owner.firstName, owner.lastName);
        return ownerRepository.save(owner);
    }

    public Owner update(Long id, Owner updated) {
        Log.infof("Updating owner id=%d", id);
        Owner owner = ownerRepository.getById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));
        owner.firstName = updated.firstName;
        owner.lastName = updated.lastName;
        owner.phone = updated.phone;
        owner.address = updated.address;
        return ownerRepository.save(owner);
    }

    public void delete(Long id) {
        Log.infof("Deleting owner id=%d", id);
        Owner owner = ownerRepository.getById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));
        ownerRepository.delete(owner);
    }
}
