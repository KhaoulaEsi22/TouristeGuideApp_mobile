package com.yourcompany.touristappbackend.Repository;

import com.yourcompany.touristappbackend.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuideRepository extends JpaRepository<Guide, UUID> {
    Optional<Guide> findByEmail(String email);
}



