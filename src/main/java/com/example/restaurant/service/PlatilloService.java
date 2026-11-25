package com.example.restaurant.service;

import com.example.restaurant.model.Platillo;
import com.example.restaurant.repository.PlatilloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlatilloService {

    private final PlatilloRepository platilloRepository;

    public PlatilloService(PlatilloRepository platilloRepository) {
        this.platilloRepository = platilloRepository;
    }

    @Transactional(readOnly = true)
    public List<Platillo> findAll() {
        return platilloRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Platillo findById(Long id) {
        return platilloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Platillo no encontrado con id: " + id));
    }

    public Platillo create(Platillo platillo) {
        return platilloRepository.save(platillo);
    }

    public Platillo update(Long id, Platillo platilloActualizado) {
        Platillo existente = findById(id);
        existente.setNombre(platilloActualizado.getNombre());
        existente.setPrecio(platilloActualizado.getPrecio());
        existente.setDisponible(platilloActualizado.getDisponible());
        return platilloRepository.save(existente);
    }

    public void delete(Long id) {
        Platillo existente = findById(id);
        platilloRepository.delete(existente);
    }
}