package com.demo.service;

import com.demo.entity.OwnerEntity;
import com.demo.repository.OwnerRepository;
import com.demo.service.exception.OwnerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerService ownerService;

    @Test
    void findAll_shouldReturnList() {
        List<OwnerEntity> ownerEntities = List.of(new OwnerEntity(), new OwnerEntity());
        when(ownerRepository.getAll()).thenReturn(ownerEntities);

        List<OwnerEntity> result = ownerService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnOwner() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.id = 1L;
        ownerEntity.firstName = "John";
        ownerEntity.lastName = "Doe";

        when(ownerRepository.getById(1L)).thenReturn(Optional.of(ownerEntity));

        OwnerEntity result = ownerService.findById(1L);

        assertNotNull(result);
        assertEquals("John", result.firstName);
    }

    @Test
    void findById_notFound_shouldThrowException() {
        when(ownerRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> ownerService.findById(99L));
    }

    @Test
    void create_shouldSaveAndReturnOwner() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.firstName = "Jane";
        ownerEntity.lastName = "Smith";
        ownerEntity.phone = "555-5678";

        when(ownerRepository.save(any(OwnerEntity.class))).thenReturn(ownerEntity);

        OwnerEntity result = ownerService.create(ownerEntity);

        assertEquals("Jane", result.firstName);
        verify(ownerRepository).save(ownerEntity);
    }

    @Test
    void update_shouldUpdateFieldsAndSave() {
        OwnerEntity existing = new OwnerEntity();
        existing.id = 1L;
        existing.firstName = "Bob";
        existing.lastName = "Brown";
        existing.phone = "555-0000";

        OwnerEntity updated = new OwnerEntity();
        updated.firstName = "Bobby";
        updated.lastName = "Brown";
        updated.phone = "555-0001";
        updated.address = "789 Pine Rd";

        when(ownerRepository.getById(1L)).thenReturn(Optional.of(existing));

        OwnerEntity result = ownerService.update(1L, updated);

        assertEquals("Bobby", result.firstName);
        assertEquals("555-0001", result.phone);
    }

    @Test
    void update_notFound_shouldThrowException() {
        when(ownerRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> ownerService.update(99L, new OwnerEntity()));
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.id = 1L;

        when(ownerRepository.getById(1L)).thenReturn(Optional.of(ownerEntity));

        ownerService.delete(1L);

        verify(ownerRepository).delete(ownerEntity);
    }

    @Test
    void delete_notFound_shouldThrowException() {
        when(ownerRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> ownerService.delete(99L));
    }
}
