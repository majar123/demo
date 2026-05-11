package com.demo.service;

import com.demo.entity.OwnerEntity;
import com.demo.entity.PetEntity;
import com.demo.entity.PetEntity.PetType;
import com.demo.repository.PetRepository;
import com.demo.service.exception.PetNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetService petService;

    @Test
    void findAll_shouldReturnList() {
        List<PetEntity> petEntities = List.of(new PetEntity(), new PetEntity());
        when(petRepository.getAll()).thenReturn(petEntities);

        List<PetEntity> result = petService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnPet() {
        PetEntity petEntity = new PetEntity();
        petEntity.id = 1L;
        petEntity.name = "Buddy";
        petEntity.type = PetType.DOG;

        when(petRepository.getById(1L)).thenReturn(Optional.of(petEntity));

        PetEntity result = petService.findById(1L);

        assertNotNull(result);
        assertEquals("Buddy", result.name);
    }

    @Test
    void findById_notFound_shouldThrowException() {
        when(petRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.findById(99L));
    }

    @Test
    void create_shouldSaveAndReturnPet() {
        PetEntity petEntity = new PetEntity();
        petEntity.name = "Whiskers";
        petEntity.type = PetType.CAT;

        when(petRepository.save(any(PetEntity.class))).thenReturn(petEntity);

        PetEntity result = petService.create(petEntity);

        assertEquals("Whiskers", result.name);
        verify(petRepository).save(petEntity);
    }

    @Test
    void update_shouldUpdateFieldsAndSave() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.id = 1L;

        PetEntity existing = new PetEntity();
        existing.id = 1L;
        existing.name = "Buddy";
        existing.type = PetType.DOG;
        existing.ownerEntity = ownerEntity;

        PetEntity updated = new PetEntity();
        updated.name = "Buddy Jr";
        updated.birthDate = LocalDate.of(2021, 1, 1);
        updated.type = PetType.DOG;
        updated.ownerEntity = ownerEntity;

        when(petRepository.getById(1L)).thenReturn(Optional.of(existing));

        PetEntity result = petService.update(1L, updated);

        assertEquals("Buddy Jr", result.name);
    }

    @Test
    void update_notFound_shouldThrowException() {
        when(petRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.update(99L, new PetEntity()));
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        OwnerEntity ownerEntity = new OwnerEntity();
        PetEntity petEntity = new PetEntity();
        petEntity.id = 1L;
        petEntity.ownerEntity = ownerEntity;
        ownerEntity.petEntities.add(petEntity);

        when(petRepository.getById(1L)).thenReturn(Optional.of(petEntity));

        petService.delete(1L);

        verify(petRepository).delete(petEntity);
    }

    @Test
    void delete_notFound_shouldThrowException() {
        when(petRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.delete(99L));
    }

    @Test
    void findByOwner_shouldReturnPets() {
        List<PetEntity> petEntities = List.of(new PetEntity(), new PetEntity());
        when(petRepository.getByOwnerId(1L)).thenReturn(petEntities);

        List<PetEntity> result = petService.findByOwner(1L);

        assertEquals(2, result.size());
    }
}
