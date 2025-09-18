package com.upb.pigmentos_api.controller;

import com.upb.pigmentos_api.dto.ColorRequestDTO;
import com.upb.pigmentos_api.model.Color;
import com.upb.pigmentos_api.model.Pigmento;
import com.upb.pigmentos_api.interfaces.IColorService;
import com.upb.pigmentos_api.interfaces.IPigmentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/colores")
public class ColorController {

    @Autowired
    private IColorService colorService;

    @Autowired
    private IPigmentoService pigmentoService;

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
        return ResponseEntity.ok(pigmentoService.findByColorPrincipalId(color_id));
    }

    @PostMapping
    public ResponseEntity<Color> create(@Valid @RequestBody ColorRequestDTO dto) {
        Color color = new Color();
        color.setNombre(dto.nombre());
        color.setCodigoHexadecimal(dto.codigoHexadecimal());

        return ResponseEntity.status(HttpStatus.CREATED).body(colorService.create(color));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> update(@PathVariable UUID id, @Valid @RequestBody ColorRequestDTO dto) {
        Color color = new Color();
        color.setNombre(dto.nombre());
        color.setCodigoHexadecimal(dto.codigoHexadecimal());

        return ResponseEntity.ok(colorService.update(id, color));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        colorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
