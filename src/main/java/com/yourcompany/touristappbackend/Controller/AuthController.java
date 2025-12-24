package com.yourcompany.touristappbackend.Controller;

import com.yourcompany.touristappbackend.Service.AuthService;
import com.yourcompany.touristappbackend.model.Role;
import com.yourcompany.touristappbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            if (email == null || email.isBlank() || password == null || password.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Email et mot de passe obligatoires"
                ));
            }

            User user = authService.authenticate(email, password);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "user", user,
                    "token", "jwt-token-placeholder"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    // =========================
    // REGISTER
    // =========================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String password = payload.get("motDePasse");
            String roleStr = payload.get("role");

            if (email == null || password == null || roleStr == null) {
                throw new Exception("Données manquantes");
            }

            Role role;
            switch (roleStr.toUpperCase()) {
                case "ROLE_GUIDE":
                    role = Role.ROLE_GUIDE;
                    break;
                case "ROLE_TOURISTE":
                default:
                    role = Role.ROLE_TOURISTE;
            }

            User user = new User();
            user.setEmail(email);
            user.setMotDePasse(password);
            user.setRole(role);
            user.setEstActif(true);

            User savedUser = authService.register(user);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "user", savedUser
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

}

