package com.camarones.clubManagment.model;

import jakarta.persistence.*;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String mesCuota;
    @NotNull
    private Date fechaVencimiento;
    @NotNull
    private boolean pagada;
    @NotNull
    private double precioCuota;
    @NotNull
    private double cantidadPagada;
    @ManyToOne
    @JoinColumn(name = "socio_id")
    private Socio socio;


}
