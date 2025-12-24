package com.yourcompany.touristappbackend.model;

import jakarta.persistence.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.UUID;

@Builder
@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @NotNull
    @Min(0)
    private Double montant;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date datePaiement;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MethodePaiement methodePaiement;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StatutDemande.StatutPaiement statut;

    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_id", nullable = false)
    private Demande demande;

    @PrePersist
    protected void onCreate() {
        this.datePaiement = new Date();
        this.statut = StatutDemande.StatutPaiement.EN_ATTENTE;
    }
}
