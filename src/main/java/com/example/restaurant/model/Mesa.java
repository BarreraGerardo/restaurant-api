package com.example.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El codigo de mesa es obligatorio")
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad minima es 1")
    @Column(nullable = false)
    private Integer capacidad;

    @NotBlank(message = "El estado es obligatorio")
    @Column(nullable = false)
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}