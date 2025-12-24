package com.yourcompany.touristappbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "evaluations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    private int note;
    private String commentaire;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "touriste_id", nullable = false)
    private Touriste touriste;

    @PrePersist
    protected void onCreate() {
        this.dateEvaluation = new Date();
    }
}
