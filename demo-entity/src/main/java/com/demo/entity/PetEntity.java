package com.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "pets")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(name = "birth_date")
    public LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    public OwnerEntity ownerEntity;

    public enum PetType {
        DOG, CAT, BIRD, RABBIT, OTHER
    }
}
