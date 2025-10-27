package com.example.Backend.model.DTO.Absence;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbsenceRequestDTO {

    private Long employeId;
    private LocalDate date;
    private String motif;

    // Justificatif (Optionnel)
    private String justificatif;
}
