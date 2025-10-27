package com.example.Backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.model.DTO.Employe.CreateEmployeDTO;
import com.example.Backend.model.DTO.Employe.EmployeResponseDTO;
import com.example.Backend.model.entity.Employe;
import com.example.Backend.repository.EmployeRepository;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;
    // Ajout d'un employe
    public EmployeResponseDTO createEmploye(CreateEmployeDTO dto) {
        Employe employe = new Employe();
        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setPoste(dto.getPoste());
        employe.setEmail(dto.getEmail());

        if (dto.getSoldeConges() != null) {
            employe.setSoldeConges(dto.getSoldeConges());
        } else {
            employe.setSoldeConges(BigDecimal.valueOf(25)); // Valeur par defaut
        }

        Employe saved = employeRepository.save(employe);

        return mapToDTO(saved);
    }
    // Liste de tout les employes
    public List<EmployeResponseDTO> getAllEmployes() {
        return employeRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Liste en fonction de l'Id
    public EmployeResponseDTO getEmployeById(Long id) {
        Employe employe = employeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employe non trouve"));
        return mapToDTO(employe);
    }

    // MAJ d'un employe
    public EmployeResponseDTO updateEmploye(Long id, CreateEmployeDTO dto) {
        Employe employe = employeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employe non trouve"));
        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setPoste(dto.getPoste());
        employe.setEmail(dto.getEmail());

        if (dto.getSoldeConges() != null) {
            employe.setSoldeConges(dto.getSoldeConges());
        }

        Employe updated = employeRepository.save(employe);
        return mapToDTO(updated);
    }

    // Suppression d'un employe
    public void deleteEmploye(Long id){
        if (!employeRepository.existsById(id)) {
            throw new RuntimeException("Employe introuvable");
        }
        employeRepository.deleteById(id);
    }

    // Mapper interne
    private EmployeResponseDTO mapToDTO(Employe employe) {
        EmployeResponseDTO dto = new EmployeResponseDTO();
        dto.setId(employe.getId());
        dto.setNom(employe.getNom());
        dto.setPrenom(employe.getPrenom());
        dto.setPoste(employe.getPoste());
        dto.setEmail(employe.getEmail());
        dto.setSoldeConges(employe.getSoldeConges());
        return dto;
    }
}
