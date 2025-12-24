package com.yourcompany.touristappbackend.Service;

import com.yourcompany.touristappbackend.Repository.GuideRepository;
import com.yourcompany.touristappbackend.Repository.UserRepository;
import com.yourcompany.touristappbackend.dto.GuideDto;
import com.yourcompany.touristappbackend.exception.ResourceNotFoundException;
import com.yourcompany.touristappbackend.model.Guide;
import com.yourcompany.touristappbackend.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuideService {

    private final GuideRepository guideRepository;
    private final UserRepository userRepository;

    public GuideService(GuideRepository guideRepository, UserRepository userRepository) {
        this.guideRepository = guideRepository;
        this.userRepository = userRepository;
    }

    public List<Guide> getAllGuides() {
        return guideRepository.findAll();
    }

    public Optional<Guide> getGuideById(UUID id) {
        return guideRepository.findById(id);
    }

    @Transactional
    public Guide createGuideFromUser(User user) {
        return guideRepository.findById(user.getId())
                .orElseGet(() -> {
                    Guide guide = new Guide();
                    guide.setBio("");
                    guide.setLanguesParlees("");
                    guide.setTarifParHeure(0.0);
                    return guideRepository.save(guide);
                });
    }

    @Transactional
    public Guide updateGuideDetails(UUID id, GuideDto guideDto) {
        Guide guide = guideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guide non trouvé"));

        guide.setNom(guideDto.getNom());
        guide.setPrenom(guideDto.getPrenom());
        guide.setEmail(guideDto.getEmail());
        guide.setTelephone(guideDto.getTelephone());
        guide.setBio(guideDto.getBio());
        guide.setLanguesParlees(guideDto.getLanguesParlees());
        guide.setTarifParHeure(guideDto.getTarifParHeure());

        return guideRepository.save(guide);
    }

    @Transactional
    public void deleteGuide(UUID id) {
        guideRepository.deleteById(id);
    }
}





