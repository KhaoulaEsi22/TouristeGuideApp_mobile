package com.yourcompany.touristappbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "guides")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 🔧 CORRECTION
public class Guide extends User {

    private String bio;
    private String languesParlees;
    private double tarifParHeure;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Demande> demandesRecues = new HashSet<>();

    public void addEvaluation(Evaluation evaluation) {
        evaluations.add(evaluation);
        evaluation.setGuide(this);
    }

    public void removeEvaluation(Evaluation evaluation) {
        evaluations.remove(evaluation);
        evaluation.setGuide(null);
    }

    public void addDemande(Demande demande) {
        demandesRecues.add(demande);
        demande.setGuide(this);
    }

    public void removeDemande(Demande demande) {
        demandesRecues.remove(demande);
        demande.setGuide(null);
    }
}
