package com.example.Backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Backend.model.DTO.Absence.AbsenceRequestDTO;
import com.example.Backend.model.DTO.Absence.AbsenceResponseDTO;
import com.example.Backend.model.entity.Absence;
import com.example.Backend.model.entity.Employe;
import com.example.Backend.model.entity.User;
import com.example.Backend.repository.AbsenceRepository;
import com.example.Backend.repository.EmployeRepository;
import com.example.Backend.repository.UserRepository;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final EmployeRepository employeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public AbsenceService(AbsenceRepository absenceRepository, 
                         EmployeRepository employeRepository,
                         UserRepository userRepository,
                         NotificationService notificationService) {
        this.absenceRepository = absenceRepository;
        this.employeRepository = employeRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    // Creer une absence
    public AbsenceResponseDTO createAbsence(AbsenceRequestDTO dto) {
        Employe employe = employeRepository.findById(dto.getEmployeId())
                .orElseThrow(() -> new RuntimeException("Employe introuvable"));
        
        Absence absence = new Absence();
        absence.setEmploye(employe);
        absence.setDate(dto.getDate());
        absence.setMotif(dto.getMotif());
        absence.setJustificatif(dto.getJustificatif());

        Absence saved = absenceRepository.save(absence);

        // 🔔 NOTIFICATION: Envoyer une notification à l'ADMIN
        User admin = userRepository.findByRole("ADMIN");
        if (admin != null) {
            String message = "Nouvelle déclaration d'absence pour " + employe.getNom() + " " + employe.getPrenom() + " - " + dto.getDate();
            notificationService.sendNotification(admin.getId(), message);
        }
        return mapToDTO(saved);
    }

    // Mettre a jour une absence
    public AbsenceResponseDTO updateAbsence(Long id, AbsenceRequestDTO dto) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence introuvable"));
        
        // Mise a jour des champs
        if (dto.getDate() != null) absence.setDate(dto.getDate());
        if (dto.getMotif() != null) absence.setMotif(dto.getMotif());
        if (dto.getJustificatif() != null) absence.setJustificatif(dto.getJustificatif());

        Absence updated = absenceRepository.save(absence);

        // 🔔 NOTIFICATION: Notifier l'admin de la modification
        User admin = userRepository.findByRole("ADMIN");
        if (admin != null) {
            String message = "Absence modifiée pour " + absence.getEmploye().getNom() + " " + absence.getEmploye().getPrenom();
            notificationService.sendNotification(admin.getId(), message);
        }

        return mapToDTO(updated);
    }

    // Supprimer une absence
    public void deleteAbsence(Long id) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence introuvable"));
        absenceRepository.delete(absence);

        Employe employe = absence.getEmploye();
        absenceRepository.delete(absence);

        // 🔔 NOTIFICATION: Notifier l'admin de la suppression
        User admin = userRepository.findByRole("ADMIN");
        if (admin != null) {
            String message = "Absence supprimée pour " + employe.getNom() + " " + employe.getPrenom() + " - " + absence.getDate();
            notificationService.sendNotification(admin.getId(), message);
        }
    }

    // Ajouter un justificatif
    public AbsenceResponseDTO addJustificatif(Long id, String justificatif) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence introuvable"));
        
        absence.setJustificatif(justificatif);
        Absence updated = absenceRepository.save(absence);

        // 🔔 NOTIFICATION: Notifier l'admin du justificatif ajouté
        User admin = userRepository.findByRole("ADMIN");
        if (admin != null) {
            String message = "Justificatif ajouté pour l'absence de " + absence.getEmploye().getNom() + " " + absence.getEmploye().getPrenom();
            notificationService.sendNotification(admin.getId(), message);
        }

        return mapToDTO(updated);
    }

    // Recuperer toutes les absences
    public List<AbsenceResponseDTO> getAllAbsences() {
        return absenceRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Recuperer absences par employeId
    public List<AbsenceResponseDTO> getAbsencesByEmployeId(Long employeId) {
        return absenceRepository.findByEmployeId(employeId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Recuperer absences par date
    public List<AbsenceResponseDTO> getAbsenceByDate(LocalDate date) {
        return absenceRepository.findByDate(date)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Recuperer absences dans une periode
    public List<AbsenceResponseDTO> getAbsencesBetween(LocalDate startDate, LocalDate endDate) {
        return absenceRepository.findByDateBetween(startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    // Recuperer absences par son Id
    public AbsenceResponseDTO getAbsenceById(Long id) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence introuvable"));
        return mapToDTO(absence);
    }

    // Recuperer absences d'un employe dans une periode
    public List<AbsenceResponseDTO> getAbsencesByEmployeIdAndBetween(Long employeId, LocalDate startDate, LocalDate endDate) {
        return absenceRepository.findByEmployeIdAndDateBetween(employeId, startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Absences avec justificatif
    public List<AbsenceResponseDTO> getAbsencesWithJustificatif() {
        return absenceRepository.findByJustificatifNotNull()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Absences sans justificatif
    public List<AbsenceResponseDTO> getAbsencesWithoutJustificatif() {
        return absenceRepository.findByJustificatifNull()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    } 

    // Mapper Absence
    private AbsenceResponseDTO mapToDTO(Absence absence) {
        AbsenceResponseDTO dto = new AbsenceResponseDTO();
        dto.setId(absence.getId());
        dto.setEmployeId(absence.getEmploye().getId());
        dto.setEmployeNom(absence.getEmploye().getNom());
        dto.setEmployePrenom(absence.getEmploye().getPrenom());
        dto.setDate(absence.getDate());
        dto.setMotif(absence.getMotif());
        dto.setJustificatif(absence.getJustificatif());
        return dto;
    }
}
