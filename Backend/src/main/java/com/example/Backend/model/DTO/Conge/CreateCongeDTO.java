package com.example.Backend.model.DTO.Conge;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCongeDTO {
    private Long employeId;
    private Long typeCongeId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String commentaire;
}
