package com.upb.pigmentos_api.repository;

import com.upb.pigmentos_api.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ColorRepository extends JpaRepository<Color, UUID> {
    Optional<Color> findByNombre(String nombre);
    Optional<Color> findByCodigoHexadecimal(String codigoHexadecimal);
}
