package com.example.Backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.model.DTO.Authentification.AuthResponseDTO;
import com.example.Backend.model.DTO.Authentification.LoginRequestDTO;
import com.example.Backend.model.DTO.Authentification.RegisterRequestDTO;
import com.example.Backend.model.entity.User;
import com.example.Backend.service.AuthService;


@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins= "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService service) {
        this.authService = service;
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }

    @PostMapping("/logout")
    public String logout() {
        return "Déconnecté avec succès. Supprimez le token côté client.";
    }

    @GetMapping("/me")
    public AuthResponseDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Récupère le username depuis le token

        User user = authService.findByUsername(username);
        Long empId = user.getEmploye() != null ? user.getEmploye().getId() : null;

        return new AuthResponseDTO(null, user.getUsername(), user.getRole(), empId);
    }
}
