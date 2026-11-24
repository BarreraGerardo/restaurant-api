package com.example.restaurant.controller;

import com.example.restaurant.model.DetallePedido;
import com.example.restaurant.model.Pedido;
import com.example.restaurant.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getAll() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @PostMapping("/mesa/{mesaId}")
    public ResponseEntity<Pedido> create(@PathVariable Long mesaId, @Valid @RequestBody Pedido pedido) {
        Pedido created = pedidoService.create(mesaId, pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/detalles")
    public ResponseEntity<Pedido> updateDetalles(@PathVariable Long id, @Valid @RequestBody List<DetallePedido> detalles) {
        return ResponseEntity.ok(pedidoService.updateDetalles(id, detalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}