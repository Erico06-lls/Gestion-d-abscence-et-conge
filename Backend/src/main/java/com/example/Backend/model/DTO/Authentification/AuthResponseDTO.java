package com.example.Backend.model.DTO.Authentification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private String token;
    private String username;
    private String role;
    private Long employeId; // null si pas lié

        public AuthResponseDTO(String token, String username, String role, Long employeId) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.employeId = employeId;
    }
}
