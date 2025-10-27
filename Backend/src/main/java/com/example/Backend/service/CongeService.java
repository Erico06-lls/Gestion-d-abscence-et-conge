package com.example.Backend.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.model.DTO.Conge.CongeResponseDTO;
import com.example.Backend.model.DTO.Conge.CreateCongeDTO;
import com.example.Backend.model.entity.Conge;
import com.example.Backend.model.entity.Employe;
import com.example.Backend.model.entity.StatutConge;
import com.example.Backend.model.entity.TypeConge;
import com.example.Backend.repository.CongeRepository;
import com.example.Backend.repository.EmployeRepository;
import com.example.Backend.repository.TypeCongeRepository;

@Service
public class CongeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private CongeRepository congeRepository;

    @Autowired
    private TypeCongeRepository typeCongeRepository;

    // Creation d'un conge(demande de conge)
    public CongeResponseDTO createConge(CreateCongeDTO dto) {
        Employe employe = employeRepository.findById(dto.getEmployeId()).orElseThrow(() -> new RuntimeException("Employe introuvable"));

        TypeConge typeConge = typeCongeRepository.findById(dto.getTypeCongeId()).orElseThrow(() -> new RuntimeException("Type de conge introuvable"));
        
        long jours = ChronoUnit.DAYS.between(dto.getDateDebut(), dto.getDateFin()) + 1;

        // Verification du solde
        if (employe.getSoldeConges().compareTo(BigDecimal.valueOf(jours)) < 0) {
            throw new RuntimeException("Solde de conge insuffisant");
        }

        Conge conge = new Conge();
        conge.setEmploye(employe);
        conge.setTypeCoge(typeConge);
        conge.setDateDebut(dto.getDateDebut());
        conge.setDateFin(dto.getDateFin());
        conge.setNombreJours((int) jours);
        conge.setCommentaire(dto.getCommentaire());
        conge.setStatut(StatutConge.EN_ATTENTE);

        Conge saved = congeRepository.save(conge);
        return mapToDTO(saved);
    }

    // Liste de tout les conge
    public List<CongeResponseDTO> getAllConges() {
        return  congeRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Récupération par statut
    public List<CongeResponseDTO> getCongeByStatut(StatutConge statut) {
        return congeRepository.findByStatut(statut)
                            .stream()
                            .map(this::mapToDTO)
                            .collect(Collectors.toList());
    }

    // Récupération par employeId
    public List<CongeResponseDTO> getCongeByEmployeId(Long employeId) {
        return congeRepository.findByEmployeId(employeId)
                            .stream()
                            .map(this::mapToDTO)
                            .collect(Collectors.toList());
    }

    //Approbation d'un conge
    public CongeResponseDTO approuverConge(Long id) {
        Conge conge = congeRepository.findById(id).orElseThrow(() -> new RuntimeException("Conge introuvable"));

        Employe employe = conge.getEmploye();

        BigDecimal nouveauSolde = employe.getSoldeConges().subtract(BigDecimal.valueOf(conge.getNombreJours()));
        employe.setSoldeConges(nouveauSolde);

        conge.setStatut(StatutConge.APPROUVE);
        employeRepository.save(employe);

        return mapToDTO(conge);
    }

    //Rejeter un conge
    public CongeResponseDTO rejeterConge(Long id) {
        Conge conge = congeRepository.findById(id).orElseThrow(() -> new RuntimeException("Conge introuvable"));

        conge.setStatut(StatutConge.REJETE);
        congeRepository.save(conge);

        return mapToDTO(conge);
    }
    // mapper
    private CongeResponseDTO mapToDTO(Conge conge) {
        CongeResponseDTO dto = new CongeResponseDTO();
        dto.setId(conge.getId());
        dto.setEmployeId(conge.getEmploye().getId());
        dto.setEmployeNom(conge.getEmploye().getNom() + " " + conge.getEmploye().getPrenom());
        dto.setTypeConge(conge.getTypeCoge().getNom());
        dto.setDateDebut(conge.getDateDebut());
        dto.setDateFin(conge.getDateFin());
        dto.setNombreJours(conge.getNombreJours());
        dto.setStatut(conge.getStatut().name());
        dto.setCommentaire(conge.getCommentaire());
        return dto;
    }
}
