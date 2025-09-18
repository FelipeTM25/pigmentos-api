package com.upb.pigmentos_api.controller;

import com.upb.pigmentos_api.dto.PigmentoRequestDTO;
import com.upb.pigmentos_api.model.Color;
import com.upb.pigmentos_api.model.FamiliaQuimica;
import com.upb.pigmentos_api.model.Pigmento;
import com.upb.pigmentos_api.interfaces.IPigmentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pigmentos")
public class PigmentoController {

    @Autowired
    private IPigmentoService service;

    @GetMapping
    public ResponseEntity<List<Pigmento>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pigmento> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pigmento> create(@Valid @RequestBody PigmentoRequestDTO dto) {
        Pigmento pigmento = new Pigmento();
        pigmento.setNombreComercial(dto.nombreComercial());
        pigmento.setFormulaQuimica(dto.formulaQuimica());
        pigmento.setNumeroCi(dto.numeroCi());

        // Relacionar entidades por ID
        FamiliaQuimica familia = new FamiliaQuimica();
        familia.setId(dto.familiaQuimicaId());
        pigmento.setFamiliaQuimica(familia);

        Color color = new Color();
        color.setId(dto.colorPrincipalId());
        pigmento.setColorPrincipal(color);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(pigmento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pigmento> update(@PathVariable UUID id, @Valid @RequestBody PigmentoRequestDTO dto) {
        Pigmento pigmento = new Pigmento();
        pigmento.setNombreComercial(dto.nombreComercial());
        pigmento.setFormulaQuimica(dto.formulaQuimica());
        pigmento.setNumeroCi(dto.numeroCi());

        FamiliaQuimica familia = new FamiliaQuimica();
        familia.setId(dto.familiaQuimicaId());
        pigmento.setFamiliaQuimica(familia);

        Color color = new Color();
        color.setId(dto.colorPrincipalId());
        pigmento.setColorPrincipal(color);

        return ResponseEntity.ok(service.update(id, pigmento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
