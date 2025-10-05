package com.example.Backend.model.DTO.Employe;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeResponseDTO {
    private Long id;
    private String nom;
    private  String prenom;
    private String poste;
    private String email;
    private BigDecimal soldeConges;
}
