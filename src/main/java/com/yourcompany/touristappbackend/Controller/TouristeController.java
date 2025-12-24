package com.yourcompany.touristappbackend.Controller;

import com.yourcompany.touristappbackend.model.*;
import com.yourcompany.touristappbackend.Service.TouristeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/touristes")
public class TouristeController {

    private final TouristeService touristeService;

    public TouristeController(TouristeService touristeService) {
        this.touristeService = touristeService;
    }

    @GetMapping
    public ResponseEntity<List<Touriste>> getAllTouristes() {
        return ResponseEntity.ok(touristeService.getAllTouristes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Touriste> getTouristeById(@PathVariable UUID id) {
        return ResponseEntity.ok(touristeService.getTouristeById(id));
    }

    @PostMapping
    public ResponseEntity<Touriste> createTouriste(@Valid @RequestBody Touriste touriste) {
        return new ResponseEntity<>(touristeService.createTouriste(touriste), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Touriste> updateTouriste(
            @PathVariable UUID id,
            @Valid @RequestBody Touriste touristeDetails) {

        return ResponseEntity.ok(touristeService.updateTouriste(id, touristeDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTouriste(@PathVariable UUID id) {
        touristeService.deleteTouriste(id);
        return ResponseEntity.noContent().build();
    }

    // ===========================
    // ACTIONS MÉTIER DU TOURISTE
    // ===========================

    @PatchMapping("/{touristeId}/demandes/{demandeId}/annuler")
    public ResponseEntity<Boolean> annulerDemande(
            @PathVariable UUID touristeId,
            @PathVariable UUID demandeId) {

        return ResponseEntity.ok(
                touristeService.annulerDemande(touristeId, demandeId)
        );
    }

    @PostMapping("/{touristeId}/paiements")
    public ResponseEntity<Paiement> effectuerPaiement(
            @PathVariable UUID touristeId,
            @RequestBody Map<String, Object> paiementDetails) {

        UUID demandeId = UUID.fromString(paiementDetails.get("demandeId").toString());
        Double montant = Double.parseDouble(paiementDetails.get("montant").toString());
        MethodePaiement methode = MethodePaiement.valueOf(
                paiementDetails.get("methodePaiement").toString()
        );

        Paiement paiement = touristeService.effectuerPaiement(
                touristeId,
                demandeId,
                montant,
                methode
        );

        return new ResponseEntity<>(paiement, HttpStatus.CREATED);
    }

    @PostMapping("/{touristeId}/evaluations")
    public ResponseEntity<Evaluation> evaluerGuide(
            @PathVariable UUID touristeId,
            @RequestBody Map<String, Object> evaluationDetails) {

        UUID guideId = UUID.fromString(evaluationDetails.get("guideId").toString());
        int note = Integer.parseInt(evaluationDetails.get("note").toString());
        String commentaire = evaluationDetails.get("commentaire").toString();

        Evaluation evaluation = touristeService.evaluerGuide(
                touristeId,
                guideId,
                note,
                commentaire
        );

        return new ResponseEntity<>(evaluation, HttpStatus.CREATED);
    }

    @PatchMapping("/{touristeId}/demandes/{demandeId}/confirmer-visite")
    public ResponseEntity<Boolean> confirmerVisite(
            @PathVariable UUID touristeId,
            @PathVariable UUID demandeId) {

        return ResponseEntity.ok(
                touristeService.confirmerVisite(touristeId, demandeId)
        );
    }
}

