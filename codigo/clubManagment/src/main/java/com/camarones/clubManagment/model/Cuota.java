package com.camarones.clubManagment.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cuota {

    @Id
    private Integer id;

    private String mesCuota;

    private LocalDate fechaVencimiento;

    private boolean pagada;

    private double precioCuota;

    @ManyToOne
    @JoinColumn(name = "socio_id")
    private Socio socio;
}
