package com.camarones.clubManagment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nombreGasto;
    @NotNull
    private Date fecha;
    @NotNull
    private double montoGasto;
    @NotNull
    private String metodoPago;
    @NotNull
    private String detalleGasto;
    @ManyToOne
    @JoinColumn(name ="proveedor_id")
    private Proovedor proovedor;
}
