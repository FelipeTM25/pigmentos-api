package com.upb.pigmentos_api.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "familias_quimicas", schema = "pigmentos")
@Data
public class FamiliaQuimica {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String nombre;
    private String descripcion;
}
