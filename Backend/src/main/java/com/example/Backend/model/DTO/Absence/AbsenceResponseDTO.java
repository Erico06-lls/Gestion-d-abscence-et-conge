package com.example.Backend.model.DTO.Absence;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbsenceResponseDTO {

    private Long id;
    private Long employeId;
    private String employeNom;
    private String employePrenom;
    private LocalDate date;
    private String motif;
    private String justificatif;
}
