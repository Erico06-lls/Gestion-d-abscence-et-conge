package com.example.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.model.entity.TypeConge;

public interface TypeCongeRepository extends  JpaRepository<TypeConge, Long> {
    boolean existsByNom(String nom);
}
