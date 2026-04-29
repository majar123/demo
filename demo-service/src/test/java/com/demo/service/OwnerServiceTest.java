package com.demo.service;

import com.demo.entity.Owner;
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
        List<Owner> owners = List.of(new Owner(), new Owner());
        when(ownerRepository.getAll()).thenReturn(owners);

        List<Owner> result = ownerService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnOwner() {
        Owner owner = new Owner();
        owner.id = 1L;
        owner.firstName = "John";
        owner.lastName = "Doe";

        when(ownerRepository.getById(1L)).thenReturn(Optional.of(owner));

        Owner result = ownerService.findById(1L);

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
        Owner owner = new Owner();
        owner.firstName = "Jane";
        owner.lastName = "Smith";
        owner.phone = "555-5678";

        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        Owner result = ownerService.create(owner);

        assertEquals("Jane", result.firstName);
        verify(ownerRepository).save(owner);
    }

    @Test
    void update_shouldUpdateFieldsAndSave() {
        Owner existing = new Owner();
        existing.id = 1L;
        existing.firstName = "Bob";
        existing.lastName = "Brown";
        existing.phone = "555-0000";

        Owner updated = new Owner();
        updated.firstName = "Bobby";
        updated.lastName = "Brown";
        updated.phone = "555-0001";
        updated.address = "789 Pine Rd";

        when(ownerRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(ownerRepository.save(existing)).thenReturn(existing);

        Owner result = ownerService.update(1L, updated);

        assertEquals("Bobby", result.firstName);
        assertEquals("555-0001", result.phone);
        verify(ownerRepository).save(existing);
    }

    @Test
    void update_notFound_shouldThrowException() {
        when(ownerRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> ownerService.update(99L, new Owner()));
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        Owner owner = new Owner();
        owner.id = 1L;

        when(ownerRepository.getById(1L)).thenReturn(Optional.of(owner));

        ownerService.delete(1L);

        verify(ownerRepository).delete(owner);
    }

    @Test
    void delete_notFound_shouldThrowException() {
        when(ownerRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> ownerService.delete(99L));
    }
}
