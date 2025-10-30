package com.example.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.model.entity.Notification;

public interface  NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByIdDesc(Long userId);

    // Récupérer les notifications non lues d'un utilisateur
    List<Notification> findByUserIdAndIsReadFalseOrderByIdDesc(Long userId);

    Notification findNotificationById(Long id);
}
