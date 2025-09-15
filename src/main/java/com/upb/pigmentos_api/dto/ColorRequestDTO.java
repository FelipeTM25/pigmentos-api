package com.upb.pigmentos_api.dto;

import jakarta.validation.constraints.NotBlank;


public record ColorRequestDTO(
        @NotBlank String nombre,
        @NotBlank String codigoHexadecimal
) {}
