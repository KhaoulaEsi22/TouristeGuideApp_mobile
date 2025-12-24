package com.yourcompany.touristappbackend.Controller;

import com.yourcompany.touristappbackend.Service.GuideService;
import com.yourcompany.touristappbackend.dto.GuideDto;
import com.yourcompany.touristappbackend.exception.ResourceNotFoundException;
import com.yourcompany.touristappbackend.model.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/guides")
@CrossOrigin(origins = "*")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @GetMapping
    public ResponseEntity<List<GuideDto>> getAllGuides() {
        List<GuideDto> guides = guideService.getAllGuides()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(guides);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuideDto> getGuideById(@PathVariable UUID id) {
        Guide guide = guideService.getGuideById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guide non trouvé avec l'ID : " + id));

        return ResponseEntity.ok(convertToDto(guide));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<GuideDto> updateGuideDetails(
            @PathVariable UUID id,
            @RequestBody GuideDto guideDto) {

        Guide updatedGuide = guideService.updateGuideDetails(id, guideDto);
        return ResponseEntity.ok(convertToDto(updatedGuide));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuide(@PathVariable UUID id) {
        guideService.deleteGuide(id);
        return ResponseEntity.noContent().build();
    }

    private GuideDto convertToDto(Guide guide) {
        GuideDto dto = new GuideDto();
        dto.setId(guide.getId());
        dto.setBio(guide.getBio());
        dto.setLanguesParlees(guide.getLanguesParlees());
        dto.setTarifParHeure(guide.getTarifParHeure());
        return dto;
    }

}
