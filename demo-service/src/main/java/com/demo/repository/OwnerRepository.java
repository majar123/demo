package com.demo.repository;

import com.demo.entity.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {

    List<Owner> getAll();

    Optional<Owner> getById(Long id);

    Owner save(Owner owner);

    void delete(Owner owner);
}
