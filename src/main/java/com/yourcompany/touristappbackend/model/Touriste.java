package com.yourcompany.touristappbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "touristes")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 🔧 CORRECTION
public class Touriste extends User {

    private String preferencesVoyage;
    private String languePreferee;
    private String nationalite;

    @OneToMany(mappedBy = "touriste", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private Set<Demande> demandesFaites = new HashSet<>();

    @OneToMany(mappedBy = "touriste", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Evaluation> evaluationsFaites = new HashSet<>();

    public void addDemande(Demande demande) {
        demandesFaites.add(demande);
        demande.setTouriste(this);
    }

    public void removeDemande(Demande demande) {
        demandesFaites.remove(demande);
        demande.setTouriste(null);
    }

    public void addEvaluation(Evaluation evaluation) {
        evaluationsFaites.add(evaluation);
        evaluation.setTouriste(this);
    }

    public void removeEvaluation(Evaluation evaluation) {
        evaluationsFaites.remove(evaluation);
        evaluation.setTouriste(null);
    }
}


