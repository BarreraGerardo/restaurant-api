package com.example.restaurant.service;

import com.example.restaurant.model.Mesa;
import com.example.restaurant.repository.MesaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Transactional(readOnly = true)
    public List<Mesa> findAll() {
        return mesaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mesa findById(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada con id: " + id));
    }

    public Mesa create(Mesa mesa) {
        if (mesa.getCodigo() != null && mesaRepository.existsByCodigo(mesa.getCodigo())) {
            throw new IllegalArgumentException("Ya existe una mesa con el codigo: " + mesa.getCodigo());
        }
        if (mesa.getEstado() == null || mesa.getEstado().isBlank()) {
            mesa.setEstado("DISPONIBLE");
        }
        return mesaRepository.save(mesa);
    }

    public Mesa update(Long id, Mesa mesaActualizada) {
        Mesa existente = findById(id);

        if (mesaActualizada.getCodigo() != null
                && !mesaActualizada.getCodigo().equals(existente.getCodigo())
                && mesaRepository.existsByCodigo(mesaActualizada.getCodigo())) {
            throw new IllegalArgumentException("Ya existe otra mesa con el codigo: " + mesaActualizada.getCodigo());
        }

        existente.setCodigo(mesaActualizada.getCodigo());
        existente.setCapacidad(mesaActualizada.getCapacidad());
        existente.setEstado(mesaActualizada.getEstado());

        return mesaRepository.save(existente);
    }

    public void delete(Long id) {
        Mesa existente = findById(id);
        mesaRepository.delete(existente);
    }
}