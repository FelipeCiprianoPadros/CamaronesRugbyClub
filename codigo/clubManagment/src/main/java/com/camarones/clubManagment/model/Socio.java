package com.camarones.clubManagment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;

    private String contacto;
    @NotNull
    private Date fechaNacimiento;
    @NotNull
    private Integer descuentoCuota;
    @NotNull
    private String categoria;
    @NotNull
    private String diciplina;

    @OneToMany(mappedBy = "socio")
    private List<Cuota> cuotas;
}
