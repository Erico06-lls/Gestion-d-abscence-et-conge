package com.example.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.model.DTO.TypeConge.CreateTypeCongeDTO;
import com.example.Backend.model.DTO.TypeConge.TypeCongeResponseDTO;
import com.example.Backend.model.entity.TypeConge;
import com.example.Backend.repository.TypeCongeRepository;

@Service
public class TypeCongeService {

    @Autowired
    private TypeCongeRepository typeCongeRepository;

    public TypeCongeResponseDTO createTypeConge(CreateTypeCongeDTO dto) {
        if (typeCongeRepository.existsByNom(dto.getNom())) {
            throw new RuntimeException("Ce type de conge existe deja");
        }

        TypeConge type = new TypeConge();
        type.setNom(dto.getNom());
        type.setDescription(dto.getDescription());

        TypeConge saved = typeCongeRepository.save(type);
        return mapToDTO(saved);
    }

    public List<TypeCongeResponseDTO> getAll() {
        return typeCongeRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public TypeCongeResponseDTO updateTypeConge(Long id, CreateTypeCongeDTO dto) {
        TypeConge type = typeCongeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Type de conge non trouve"));
        
        type.setNom(dto.getNom());
        type.setDescription(dto.getDescription());

        TypeConge updated = typeCongeRepository.save(type);
        return mapToDTO(updated);
    }

    public void deleteTypeConge(Long id) {
        if(!typeCongeRepository.existsById(id)) {
            throw new RuntimeException("Type de conge introuvable");
        }
        typeCongeRepository.deleteById(id);
    }

    private TypeCongeResponseDTO mapToDTO(TypeConge type) {
        TypeCongeResponseDTO dto = new TypeCongeResponseDTO();
        dto.setId(type.getId());
        dto.setNom(type.getNom());
        dto.setDescription(type.getDescription());
        return dto;
    }
}
