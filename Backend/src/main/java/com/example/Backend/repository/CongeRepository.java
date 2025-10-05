package com.example.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.model.entity.Conge;

public interface CongeRepository extends JpaRepository<Conge, Long>{
    List<Conge> findByStatut(String statut);
    List<Conge> findByEmployeId(Long employeId);
}
