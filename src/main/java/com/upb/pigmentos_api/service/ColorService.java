package com.upb.pigmentos_api.service;

import com.upb.pigmentos_api.model.Color;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColorService {
    List<Color> findAll();
    Optional<Color> findById(UUID id);
    Color create(Color color);
    Color update(UUID id, Color color);
    void delete(UUID id);
}