package com.example.restaurant.controller;

import com.example.restaurant.model.Platillo;
import com.example.restaurant.service.PlatilloService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platillos")
public class PlatilloController {

    private final PlatilloService platilloService;

    public PlatilloController(PlatilloService platilloService) {
        this.platilloService = platilloService;
    }

    @GetMapping
    public ResponseEntity<List<Platillo>> getAll() {
        return ResponseEntity.ok(platilloService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Platillo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(platilloService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Platillo> create(@Valid @RequestBody Platillo platillo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platilloService.create(platillo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Platillo> update(@PathVariable Long id, @Valid @RequestBody Platillo platillo) {
        return ResponseEntity.ok(platilloService.update(id, platillo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        platilloService.delete(id);
        return ResponseEntity.noContent().build();
    }
}