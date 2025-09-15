package com.upb.pigmentos_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "pigmentos", schema = "pigmentos")
@Data
public class Pigmento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String nombreComercial;
    @Column(nullable = false)
    private String formulaQuimica;
    @Column(nullable = false, unique = true)
    private String numeroCi;
    @ManyToOne
    @JoinColumn(name = "familia_quimica", nullable = false)
    private FamiliaQuimica familiaQuimica;
    @ManyToOne
    @JoinColumn(name = "color_principal", nullable = false)
    private Color colorPrincipal;
}
