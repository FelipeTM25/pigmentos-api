package com.upb.pigmentos_api.controller;

import com.upb.pigmentos_api.model.Color;
import com.upb.pigmentos_api.model.Pigmento;
import com.upb.pigmentos_api.service.ColorService;
import com.upb.pigmentos_api.service.PigmentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/colores")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @Autowired
    private PigmentoService pigmentoService;

    @GetMapping
    public ResponseEntity<List<Color>> getAll() {
        return ResponseEntity.ok(colorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getById(@PathVariable UUID id) {
        return colorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{color_id}/pigmentos")
    public ResponseEntity<List<Pigmento>> getPigmentosByColorId(@PathVariable UUID color_id) {
        if (colorService.findById(color_id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Pigmento> pigmentos = pigmentoService.findByColorPrincipalId(color_id);
        return ResponseEntity.ok(pigmentos);
    }

    @PostMapping
    public ResponseEntity<Color> create(@RequestBody Color color) {
        return ResponseEntity.ok(colorService.create(color));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> update(@PathVariable UUID id, @RequestBody Color color) {
        return ResponseEntity.ok(colorService.update(id, color));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        colorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}