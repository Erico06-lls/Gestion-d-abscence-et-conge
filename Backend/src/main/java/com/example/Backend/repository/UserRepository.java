package com.example.Backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.model.entity.Employe;
import com.example.Backend.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    // Méthode pour retrouver un user par l'email de l'employé lié
    Optional<User> findByEmployeEmail(String email);
    User findByRole(String role); // Pour trouver le manager
    User findByEmploye(Employe employe);
}
