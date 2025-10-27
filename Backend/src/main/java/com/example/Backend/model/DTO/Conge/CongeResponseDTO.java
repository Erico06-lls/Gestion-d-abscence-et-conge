package com.example.Backend.model.DTO.Conge;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CongeResponseDTO {
    private Long id;
    private Long employeId;
    private String employeNom;
    private String typeConge;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nombreJours;
    private String statut;
    private String commentaire;
}
