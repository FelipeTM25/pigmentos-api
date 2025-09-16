package com.upb.pigmentos_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ColorRequestDTO(
        @NotBlank String nombre,
        @NotBlank
        @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "El código hexadecimal debe tener formato #RRGGBB")
        String codigoHexadecimal
) {}
