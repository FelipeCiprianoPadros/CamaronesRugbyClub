package com.camarones.clubManagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "cuota")
    private List<Socio> socios;
}
