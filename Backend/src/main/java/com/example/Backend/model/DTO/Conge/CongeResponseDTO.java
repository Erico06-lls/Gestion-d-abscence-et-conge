package com.example.Backend.model.DTO.Conge;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CongeResponseDTO {
    private Long id;
    private String employeNom;
    private String typeConge;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String status;
    private String commentaire;
}
