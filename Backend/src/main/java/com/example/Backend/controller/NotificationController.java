package com.example.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.model.entity.Notification;
import com.example.Backend.repository.NotificationRepository;
import com.example.Backend.service.NotificationService;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationRepository.findByUserIdOrderByIdDesc(userId);
    }
    
    // Marquer une notification comme lue
    @PutMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<String> markAsRead(@PathVariable Long notificationId) {
        Notification updatedNotification = notificationService.markNotificationAsRead(notificationId);
        if (updatedNotification != null) {
            return ResponseEntity.ok("Notification marquée comme lue.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint pour supprimer une notification par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.ok("Notification supprimée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Erreur lors de la suppression de la notification : " + e.getMessage());
        }
    }
}