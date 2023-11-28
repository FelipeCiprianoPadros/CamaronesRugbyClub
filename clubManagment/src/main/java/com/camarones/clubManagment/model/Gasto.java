package com.camarones.clubManagment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombreGasto;
    private LocalDate fecha;
    private double montoGasto;
    private String metodoPago;
    private String detalleGasto;
    @ManyToOne
    @JoinColumn(name ="proveedor_id")
    private Proovedor proovedor;
}
