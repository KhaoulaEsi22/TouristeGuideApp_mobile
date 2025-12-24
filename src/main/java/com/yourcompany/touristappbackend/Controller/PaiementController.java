package com.yourcompany.touristappbackend.Controller;

import com.yourcompany.touristappbackend.model.Paiement;
import com.yourcompany.touristappbackend.model.Demande;
import com.yourcompany.touristappbackend.model.StatutDemande;
import com.yourcompany.touristappbackend.model.MethodePaiement; // Importe la nouvelle enum MethodePaiement
import com.yourcompany.touristappbackend.Service.PaiementService; // Importe le service
import com.yourcompany.touristappbackend.Repository.DemandeRepository; // Nécessaire pour associer un paiement à une demande lors de la création
import com.yourcompany.touristappbackend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // Pour la validation des requêtes

import java.util.List;
import java.util.Map;
import java.util.Date; // Pour la date de paiement
import java.util.UUID;

/**
 * Contrôleur REST pour la gestion des paiements dans l'application touristique.
 * Ce contrôleur expose des endpoints pour les opérations CRUD et la gestion des statuts de paiement.
 */
@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @PatchMapping("/{id}/annuler")
    public ResponseEntity<Void> annulerPaiement(@PathVariable UUID id) {
        paiementService.annulerPaiement(id);
        return ResponseEntity.noContent().build();
    }
}

