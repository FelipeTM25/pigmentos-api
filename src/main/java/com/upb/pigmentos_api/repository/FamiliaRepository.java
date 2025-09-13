package com.upb.pigmentos_api.repository;

import com.upb.pigmentos_api.model.FamiliaQuimica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamiliaRepository extends JpaRepository<FamiliaQuimica, UUID> {
    Optional<FamiliaQuimica> findByNombre(String nombre);
}