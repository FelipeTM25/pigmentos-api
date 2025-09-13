package com.upb.pigmentos_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "colores", schema = "pigmentos")
@Data
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(nullable = false)
    private String codigoHexadecimal;
}
