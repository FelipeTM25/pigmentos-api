package com.upb.pigmentos_api.service;

import com.upb.pigmentos_api.model.FamiliaQuimica;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FamiliaService {
    List<FamiliaQuimica> findAll();
    Optional<FamiliaQuimica> findById(UUID id);
    FamiliaQuimica create(FamiliaQuimica familia);
    FamiliaQuimica update(UUID id, FamiliaQuimica familia);
    void delete(UUID id);
}