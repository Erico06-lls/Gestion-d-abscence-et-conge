package com.example.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.model.DTO.Conge.CongeResponseDTO;
import com.example.Backend.model.entity.StatutConge;
import com.example.Backend.model.DTO.Conge.CreateCongeDTO;
import com.example.Backend.service.CongeService;

@RestController
@RequestMapping("api/conges")
public class CongeController {

    @Autowired
    private CongeService congeService;

    @PostMapping
    public CongeResponseDTO create(@RequestBody CreateCongeDTO dto) {
        return congeService.createConge(dto);
    }

    @GetMapping
    public List<CongeResponseDTO> getAll() {
        return congeService.getAllConges();
    }

    @GetMapping("/{statut}/statut")
    public List<CongeResponseDTO> getCongeByStatut(@PathVariable StatutConge statut) {
        return congeService.getCongeByStatut(statut);
    }

    @GetMapping("/{employeId}/employe")
    public List<CongeResponseDTO> getCongeByEmploye(@PathVariable Long employeId) {
        return congeService.getCongeByEmployeId(employeId);
    }
    
    @PutMapping("/{id}/approuver")
    public CongeResponseDTO approuver(@PathVariable Long id) {
        return congeService.approuverConge(id);
    }

    @PutMapping("/{id}/rejeter")
    public CongeResponseDTO rejeter(@PathVariable Long id) {
        return congeService.rejeterConge(id);
    }
    
}
