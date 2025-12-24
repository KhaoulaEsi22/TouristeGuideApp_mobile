package com.yourcompany.touristappbackend.Service;

import com.yourcompany.touristappbackend.Repository.DemandeRepository;
import com.yourcompany.touristappbackend.Repository.TouristeRepository;
import com.yourcompany.touristappbackend.exception.ResourceNotFoundException;
import com.yourcompany.touristappbackend.model.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DemandeService {

    private final DemandeRepository demandeRepository;
    private final TouristeRepository touristeRepository;

    public DemandeService(
            DemandeRepository demandeRepository,
            TouristeRepository touristeRepository
    ) {
        this.demandeRepository = demandeRepository;
        this.touristeRepository = touristeRepository;
    }

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public Demande getDemandeById(UUID id) {
        return demandeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Demande non trouvée : " + id));
    }

    // 🔥 MÉTHODE CLÉ
    public Demande createDemande(Demande demande, UUID touristeId) {

        Touriste touriste = touristeRepository.findById(touristeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Touriste non trouvé"));

        demande.setTouriste(touriste);
        demande.setDateDemande(LocalDateTime.now());
        demande.setStatut(StatutDemande.EN_ATTENTE);

        return demandeRepository.save(demande);
    }
}







