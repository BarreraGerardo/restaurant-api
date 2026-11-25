package com.example.restaurant.repository;

import com.example.restaurant.model.Platillo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatilloRepository extends JpaRepository<Platillo, Long> {
}