package com.demo.repository;

import com.demo.entity.OwnerEntity;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {

    List<OwnerEntity> getAll();

    Optional<OwnerEntity> getById(Long id);

    OwnerEntity save(OwnerEntity ownerEntity);

    void delete(OwnerEntity ownerEntity);
}
