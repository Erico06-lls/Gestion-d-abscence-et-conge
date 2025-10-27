package com.example.Backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.model.DTO.Absence.AbsenceRequestDTO;
import com.example.Backend.model.DTO.Absence.AbsenceResponseDTO;
import com.example.Backend.service.AbsenceService;




@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @PostMapping
    public AbsenceResponseDTO createAbsence(@RequestBody AbsenceRequestDTO dto) {
        return absenceService.createAbsence(dto);
    }
    
    @GetMapping
    public List<AbsenceResponseDTO> getAllAbsences() {
        return absenceService.getAllAbsences();
    }
    
    @GetMapping("/{employeId}/employe")
    public List<AbsenceResponseDTO> getAbsencesByEmploye(@PathVariable Long employeId) {
        return absenceService.getAbsencesByEmployeId(employeId);
    }

    @GetMapping("/{date}/date")
    public List<AbsenceResponseDTO> getAbsencesByDate(@PathVariable LocalDate date) {
        return absenceService.getAbsenceByDate(date);
    }

    @GetMapping("/periode")
    public List<AbsenceResponseDTO> getAbsencesBetween(@RequestParam LocalDate starDate, @RequestParam LocalDate enDate) {
        return absenceService.getAbsencesBetween(enDate, enDate);
    }
    
    @GetMapping("/{employeId}/employe/periode")
    public List<AbsenceResponseDTO> getAbsencesByEmployeAndBetween(@PathVariable Long employeId, @RequestParam LocalDate starDate, @RequestParam LocalDate enDate) {
        return absenceService.getAbsencesByEmployeIdAndBetween(employeId, enDate, enDate);
    }

    @GetMapping("/justificatif")
    public List<AbsenceResponseDTO> getAbsencesWithJustificatif() {
        return absenceService.getAbsencesWithJustificatif();
    }

    @GetMapping("/sans-justificatif")
    public List<AbsenceResponseDTO> getAbsencesWithoutJustificatif() {
        return absenceService.getAbsencesWithoutJustificatif();
    }

    @PutMapping("/{id}")
    public AbsenceResponseDTO updateAbsence(@PathVariable Long id, @RequestBody AbsenceRequestDTO dto) {
        return absenceService.updateAbsence(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
    }
}
