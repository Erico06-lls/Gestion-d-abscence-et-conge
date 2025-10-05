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

import com.example.Backend.model.DTO.TypeConge.CreateTypeCongeDTO;
import com.example.Backend.model.DTO.TypeConge.TypeCongeResponseDTO;
import com.example.Backend.service.TypeCongeService;


@RestController
@RequestMapping("api/type-conge")
public class TypeCongeController {

    @Autowired
    private TypeCongeService typeCongeService;

    @PostMapping()
    public TypeCongeResponseDTO create(@RequestBody CreateTypeCongeDTO dto) {
        return typeCongeService.createTypeConge(dto);
    }

    @GetMapping()
    public List<TypeCongeResponseDTO> getAll() {
        return typeCongeService.getAll();
    }
    
    @PutMapping("/{id}")
    public TypeCongeResponseDTO update(@PathVariable Long id, @RequestBody CreateTypeCongeDTO dto) {
        return typeCongeService.updateTypeConge(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        typeCongeService.deleteTypeConge(id);
    }
}
