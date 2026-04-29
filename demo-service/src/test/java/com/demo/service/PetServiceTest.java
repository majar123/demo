package com.demo.service;

import com.demo.entity.Owner;
import com.demo.entity.Pet;
import com.demo.entity.Pet.PetType;
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
        List<Pet> pets = List.of(new Pet(), new Pet());
        when(petRepository.getAll()).thenReturn(pets);

        List<Pet> result = petService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnPet() {
        Pet pet = new Pet();
        pet.id = 1L;
        pet.name = "Buddy";
        pet.type = PetType.DOG;

        when(petRepository.getById(1L)).thenReturn(Optional.of(pet));

        Pet result = petService.findById(1L);

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
        Pet pet = new Pet();
        pet.name = "Whiskers";
        pet.type = PetType.CAT;

        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet result = petService.create(pet);

        assertEquals("Whiskers", result.name);
        verify(petRepository).save(pet);
    }

    @Test
    void update_shouldUpdateFieldsAndSave() {
        Owner owner = new Owner();
        owner.id = 1L;

        Pet existing = new Pet();
        existing.id = 1L;
        existing.name = "Buddy";
        existing.type = PetType.DOG;
        existing.owner = owner;

        Pet updated = new Pet();
        updated.name = "Buddy Jr";
        updated.birthDate = LocalDate.of(2021, 1, 1);
        updated.type = PetType.DOG;
        updated.owner = owner;

        when(petRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(petRepository.save(existing)).thenReturn(existing);

        Pet result = petService.update(1L, updated);

        assertEquals("Buddy Jr", result.name);
        verify(petRepository).save(existing);
    }

    @Test
    void update_notFound_shouldThrowException() {
        when(petRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.update(99L, new Pet()));
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        Pet pet = new Pet();
        pet.id = 1L;

        when(petRepository.getById(1L)).thenReturn(Optional.of(pet));

        petService.delete(1L);

        verify(petRepository).delete(pet);
    }

    @Test
    void delete_notFound_shouldThrowException() {
        when(petRepository.getById(99L)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.delete(99L));
    }

    @Test
    void findByOwner_shouldReturnPets() {
        List<Pet> pets = List.of(new Pet(), new Pet());
        when(petRepository.getByOwnerId(1L)).thenReturn(pets);

        List<Pet> result = petService.findByOwner(1L);

        assertEquals(2, result.size());
    }
}
