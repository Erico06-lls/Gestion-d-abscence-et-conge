package com.example.Backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Backend.model.DTO.Authentification.AuthResponseDTO;
import com.example.Backend.model.DTO.Authentification.LoginRequestDTO;
import com.example.Backend.model.DTO.Authentification.RegisterRequestDTO;
import com.example.Backend.model.entity.Employe;
import com.example.Backend.model.entity.User;
import com.example.Backend.repository.EmployeRepository;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmployeRepository employeRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, EmployeRepository employeRepository,
                       AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.employeRepository = employeRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // Inscription
    public AuthResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        // Lier l'utilisateur à un employé si le rôle est EMPLOYE
        if ("EMPLOYE".equalsIgnoreCase(dto.getRole())) {
            if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Email de l'employé requis pour un compte EMPLOYE");
            }

            Employe emp = employeRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("Employé introuvable pour lier au compte"));
            user.setEmploye(emp);
        }

        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getUsername());
        Long empId = saved.getEmploye() != null ? saved.getEmploye().getId() : null;
        return new AuthResponseDTO(token, saved.getUsername(), saved.getRole(), empId);
    }

    // Authentification
    public AuthResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
        String token = jwtUtil.generateToken(user.getUsername());
        Long empId = user.getEmploye() != null ? user.getEmploye().getId() : null;
        return new AuthResponseDTO(token, user.getUsername(), user.getRole(), empId);
    }

    public User findByUsername(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

}
