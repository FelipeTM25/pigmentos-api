package com.upb.pigmentos_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PigmentoRequestDTO(
        @NotBlank String nombreComercial,
        @NotBlank String formulaQuimica,
        @NotBlank String numeroCi,
        @NotNull UUID familiaQuimicaId,
        @NotNull UUID colorPrincipalId
) {}
