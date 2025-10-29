package com.example.Backend.model.DTO.Authentification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String role;
    private String email;
}
