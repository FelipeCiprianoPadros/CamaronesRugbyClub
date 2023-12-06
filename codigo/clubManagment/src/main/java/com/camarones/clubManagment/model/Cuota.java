package com.camarones.clubManagment.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String mesCuota;

    private Date fechaVencimiento;

    private boolean pagada;

    private double precioCuota;

    @ManyToOne
    @JoinColumn(name = "socio_id")
    private Socio socio;


}
