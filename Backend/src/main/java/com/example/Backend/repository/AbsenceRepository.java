package com.example.Backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.model.entity.Absence;


public interface AbsenceRepository extends JpaRepository<Absence, Long>{

    // Trouver toutes les absences d'un employe par son ID
    List<Absence> findByEmployeId(Long employeId);

    // Trouver les absences pour une date specifique
    List<Absence> findByDate(LocalDate date);

    // Trouver les absences dans une periode donnee
    List<Absence> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Trouver les absences d'un employe dans une periode donnee
    List<Absence> findByEmployeIdAndDateBetween(Long EmployeId, LocalDate startDate, LocalDate endDate);

    // Trouver les absences avec justificatif
    List<Absence> findByJustificatifNotNull();

    // Trouver les absences sans justificatif
    List<Absence> findByJustificatifNull();
}
