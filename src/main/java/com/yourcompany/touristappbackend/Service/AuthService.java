package com.yourcompany.touristappbackend.Service;

import com.yourcompany.touristappbackend.Repository.UserRepository;
import com.yourcompany.touristappbackend.model.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // REGISTER
    // =========================
    public User register(User input) {

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        input.setMotDePasse(passwordEncoder.encode(input.getMotDePasse()));
        input.setEstActif(true);

        if (input.getRole() == Role.ROLE_TOURISTE) {
            Touriste touriste = new Touriste();
            touriste.setEmail(input.getEmail());
            touriste.setMotDePasse(input.getMotDePasse());
            touriste.setRole(Role.ROLE_TOURISTE);
            touriste.setEstActif(true);
            return userRepository.save(touriste);
        }

        if (input.getRole() == Role.ROLE_GUIDE) {
            Guide guide = new Guide();
            guide.setEmail(input.getEmail());
            guide.setMotDePasse(input.getMotDePasse());
            guide.setRole(Role.ROLE_GUIDE);
            guide.setEstActif(true);
            return userRepository.save(guide);
        }

        throw new IllegalStateException("Rôle non supporté");
    }

    // =========================
    // LOGIN / AUTHENTICATE
    // =========================
    public User authenticate(String email, String rawPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(rawPassword, user.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        if (!Boolean.TRUE.equals(user.getEstActif())) {
            throw new RuntimeException("Compte désactivé");
        }

        return user;
    }
}




