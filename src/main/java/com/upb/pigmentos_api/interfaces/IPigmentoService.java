package com.upb.pigmentos_api.interfaces;

import com.upb.pigmentos_api.model.Pigmento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPigmentoService {
    List<Pigmento> findAll();
    Optional<Pigmento> findById(UUID id);
    Pigmento create(Pigmento pigmento);
    Pigmento update(UUID id, Pigmento pigmento);
    void delete(UUID id);
    List<Pigmento> findByFamiliaQuimicaId(UUID familiaId);
    List<Pigmento> findByColorPrincipalId(UUID colorId);
}