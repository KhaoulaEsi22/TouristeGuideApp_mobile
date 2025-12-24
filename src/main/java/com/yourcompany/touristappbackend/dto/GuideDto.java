package com.yourcompany.touristappbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideDto {
    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String bio;
    private String languesParlees;
    private double tarifParHeure;


}
