package com.upb.pigmentos_api.dto;

import jakarta.validation.constraints.NotBlank;

public record FamiliaRequestDTO(
        @NotBlank String nombre,
        String descripcion
) {}
