package com.upb.pigmentos_api.controller;

import com.upb.pigmentos_api.model.FamiliaQuimica;
import com.upb.pigmentos_api.model.Pigmento;
import com.upb.pigmentos_api.service.FamiliaService;
import com.upb.pigmentos_api.service.PigmentoService;
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
    private FamiliaService service;

    @Autowired
    private PigmentoService pigmentoService;

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
        List<Pigmento> pigmentos = pigmentoService.findByFamiliaQuimicaId(familia_id);
        return ResponseEntity.ok(pigmentos);
    }

    @PostMapping
    public ResponseEntity<FamiliaQuimica> create(@RequestBody FamiliaQuimica familia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(familia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamiliaQuimica> update(@PathVariable UUID id, @RequestBody FamiliaQuimica familia) {
        return ResponseEntity.ok(service.update(id, familia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
