package com.upb.pigmentos_api.dto;

import java.util.UUID;

public record ColorResponseDTO(
        UUID id,
        String nombre,
        String codigoHexadecimal
) {}
