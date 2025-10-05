package com.example.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.model.DTO.Employe.CreateEmployeDTO;
import com.example.Backend.model.DTO.Employe.EmployeResponseDTO;
import com.example.Backend.service.EmployeService;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @PostMapping
    public EmployeResponseDTO create(@RequestBody CreateEmployeDTO dto) {
        return employeService.createEmploye(dto);
    }
    
    @GetMapping
    public List<EmployeResponseDTO> getAll() {
        return employeService.getAllEmployes();
    }
    
    @GetMapping("/{id}")
    public EmployeResponseDTO getById(@PathVariable Long id) {
        return employeService.getEmployeById(id);
    }

    @PutMapping("/{id}")
    public EmployeResponseDTO update(@PathVariable Long id, @RequestBody CreateEmployeDTO dto) {
        return employeService.updateEmploye(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeService.deleteEmploye(id);
    }
}
