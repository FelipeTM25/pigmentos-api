package com.upb.pigmentos_api.controller;

import com.upb.pigmentos_api.model.Pigmento;
import com.upb.pigmentos_api.service.PigmentoService;
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
    private PigmentoService service;

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
    public ResponseEntity<Pigmento> create(@RequestBody Pigmento pigmento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(pigmento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pigmento> update(@PathVariable UUID id, @RequestBody Pigmento pigmento) {
        return ResponseEntity.ok(service.update(id, pigmento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
