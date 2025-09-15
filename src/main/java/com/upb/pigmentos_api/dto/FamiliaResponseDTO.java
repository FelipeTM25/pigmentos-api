package com.upb.pigmentos_api.dto;

import java.util.UUID;

public record FamiliaResponseDTO(
        UUID id,
        String nombre,
        String descripcion
) {}
