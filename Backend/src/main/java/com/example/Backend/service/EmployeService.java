package com.example.Backend.service;

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

    public EmployeResponseDTO createEmploye(CreateEmployeDTO dto) {
        Employe employe = new Employe();
        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setPoste(dto.getPoste());
        employe.setEmail(dto.getEmail());

        Employe saved = employeRepository.save(employe);

        return mapToDTO(saved);
    }

    public List<EmployeResponseDTO> getAllEmployes() {
        return employeRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public EmployeResponseDTO getEmployeById(Long id) {
        Employe employe = employeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employe non trouve"));
        return mapToDTO(employe);
    }

    public EmployeResponseDTO updateEmploye(Long id, CreateEmployeDTO dto) {
        Employe employe = employeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employe non trouve"));
        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setPoste(dto.getPoste());
        employe.setEmail(dto.getEmail());

        Employe updated = employeRepository.save(employe);
        return mapToDTO(updated);
    }

    public void deleteEmploye(Long id){
        if (!employeRepository.existsById(id)) {
            throw new RuntimeException("Employe non trouve");
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
