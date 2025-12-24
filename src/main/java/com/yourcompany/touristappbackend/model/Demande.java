package com.yourcompany.touristappbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "demandes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    private String title;
    private String description;
    private LocalDateTime date;
    private String time;
    private String location;
    private Integer numberOfPeople;
    private String language;
    private String specialRequirements;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut;

    private LocalDateTime dateDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "touriste_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Touriste touriste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Guide guide;

    @PrePersist
    protected void onCreate() {
        this.dateDemande = LocalDateTime.now();
    }
}


