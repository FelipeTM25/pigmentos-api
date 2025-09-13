package com.upb.pigmentos_api.repository;

import com.upb.pigmentos_api.model.Pigmento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PigmentoRepository extends JpaRepository<Pigmento, UUID> {

    Optional<Pigmento> findByNumeroCi(String numeroCi);

    List<Pigmento> findByFamiliaQuimicaId(UUID familiaId);

    List<Pigmento> findByColorPrincipalId(UUID colorId);
}