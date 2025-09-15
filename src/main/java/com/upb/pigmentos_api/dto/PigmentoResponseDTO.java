package com.upb.pigmentos_api.dto;

import java.util.UUID;

public record PigmentoResponseDTO(
        UUID id,
        String nombreComercial,
        String formulaQuimica,
        String numeroCi,
        String familiaNombre,
        String colorNombre
) {}
