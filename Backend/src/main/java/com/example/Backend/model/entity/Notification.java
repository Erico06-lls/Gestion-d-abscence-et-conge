package com.example.Backend.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import  lombok.Setter;

@Getter
@Setter
@Table(name="notification")
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime createAt = LocalDateTime.now();
    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable=false)
    private User user;
}
