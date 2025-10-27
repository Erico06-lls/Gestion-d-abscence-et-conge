package com.example.Backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conge {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="employe_id")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name="type_conge_id")
    private TypeConge typeCoge;

    private java.time.LocalDate dateDebut;
    private java.time.LocalDate dateFin;
    private int nombreJours;

    
    @Enumerated(EnumType.STRING)
    private StatutConge statut = StatutConge.EN_ATTENTE;

    private String commentaire;
}
