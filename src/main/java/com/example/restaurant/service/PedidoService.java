package com.example.restaurant.service;

import com.example.restaurant.model.DetallePedido;
import com.example.restaurant.model.Mesa;
import com.example.restaurant.model.Pedido;
import com.example.restaurant.repository.MesaRepository;
import com.example.restaurant.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;

    public PedidoService(PedidoRepository pedidoRepository, MesaRepository mesaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
    }

    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + id));
    }

    public Pedido create(Long mesaId, Pedido pedido) {
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada con id: " + mesaId));

        pedido.setMesa(mesa);
        if (pedido.getFecha() == null) {
            pedido.setFecha(LocalDateTime.now());
        }

        List<DetallePedido> detalles = pedido.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un detalle");
        }

        double totalCalculado = 0.0;
        for (DetallePedido detalle : detalles) {
            detalle.setPedido(pedido);
            totalCalculado += detalle.getPrecioUnitario() * detalle.getCantidad();
        }

        pedido.setTotal(totalCalculado);
        return pedidoRepository.save(pedido);
    }

    public Pedido update(Long id, Pedido pedidoActualizado) {
        Pedido existente = findById(id);

        if (pedidoActualizado.getMesa() != null && pedidoActualizado.getMesa().getId() != null) {
            Long nuevaMesaId = pedidoActualizado.getMesa().getId();
            Mesa mesa = mesaRepository.findById(nuevaMesaId)
                    .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada con id: " + nuevaMesaId));
            existente.setMesa(mesa);
        }

        if (pedidoActualizado.getFecha() != null) {
            existente.setFecha(pedidoActualizado.getFecha());
        }

        if (pedidoActualizado.getDetalles() != null && !pedidoActualizado.getDetalles().isEmpty()) {
            return updateDetalles(id, pedidoActualizado.getDetalles());
        }

        return pedidoRepository.save(existente);
    }

    public Pedido updateDetalles(Long id, List<DetallePedido> nuevosDetalles) {
        Pedido existente = findById(id);

        if (nuevosDetalles == null || nuevosDetalles.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un detalle");
        }

        existente.getDetalles().clear();

        double totalCalculado = 0.0;
        for (DetallePedido detalle : nuevosDetalles) {
            detalle.setPedido(existente);
            existente.getDetalles().add(detalle);
            totalCalculado += detalle.getPrecioUnitario() * detalle.getCantidad();
        }

        existente.setTotal(totalCalculado);
        return pedidoRepository.save(existente);
    }

    public void delete(Long id) {
        Pedido existente = findById(id);
        pedidoRepository.delete(existente);
    }
}