package com.yourcompany.touristappbackend.Controller;

import com.yourcompany.touristappbackend.Service.DemandeService;
import com.yourcompany.touristappbackend.model.Demande;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/demandes")
@CrossOrigin("*")
public class DemandeController {

    private final DemandeService demandeService;

    public DemandeController(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    // =====================
    // GET ALL
    // =====================
    @GetMapping
    public ResponseEntity<List<Demande>> getAll() {
        return ResponseEntity.ok(demandeService.getAllDemandes());
    }

    // =====================
    // GET BY ID
    // =====================
    @GetMapping("/{id}")
    public ResponseEntity<Demande> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(demandeService.getDemandeById(id));
    }

    // =====================
    // CREATE (UTILISÉ PAR LE FRONT)
    // =====================
    @PostMapping
    public ResponseEntity<Demande> createDemande(@RequestBody Map<String, Object> payload) {

        UUID touristeId = UUID.fromString(payload.get("touristeId").toString());

        Demande demande = new Demande();
        demande.setTitle(payload.get("title").toString());
        demande.setDescription(payload.get("description").toString());
        demande.setLocation(payload.get("location").toString());
        demande.setNumberOfPeople(
                Integer.parseInt(payload.get("numberOfPeople").toString())
        );

        Demande saved = demandeService.createDemande(demande, touristeId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}







