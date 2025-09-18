package com.upb.pigmentos_api.controller;

import com.upb.pigmentos_api.dto.FamiliaRequestDTO;
import com.upb.pigmentos_api.model.FamiliaQuimica;
import com.upb.pigmentos_api.model.Pigmento;
import com.upb.pigmentos_api.interfaces.IFamiliaService;
import com.upb.pigmentos_api.interfaces.IPigmentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/familias")
public class FamiliaController {

    @Autowired
    private IFamiliaService service;

    @Autowired
    private IPigmentoService pigmentoService;

    @GetMapping
    public ResponseEntity<List<FamiliaQuimica>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamiliaQuimica> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{familia_id}/pigmentos")
    public ResponseEntity<List<Pigmento>> getPigmentosByFamiliaId(@PathVariable UUID familia_id) {
        if (service.findById(familia_id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pigmentoService.findByFamiliaQuimicaId(familia_id));
    }

    @PostMapping
    public ResponseEntity<FamiliaQuimica> create(@Valid @RequestBody FamiliaRequestDTO dto) {
        FamiliaQuimica familia = new FamiliaQuimica();
        familia.setNombre(dto.nombre());
        familia.setDescripcion(dto.descripcion());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(familia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamiliaQuimica> update(@PathVariable UUID id, @Valid @RequestBody FamiliaRequestDTO dto) {
        FamiliaQuimica familia = new FamiliaQuimica();
        familia.setNombre(dto.nombre());
        familia.setDescripcion(dto.descripcion());

        return ResponseEntity.ok(service.update(id, familia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
