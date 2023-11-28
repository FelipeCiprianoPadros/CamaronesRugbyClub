package com.camarones.clubManagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Socio {

    @Id
    private Integer id;

    private String nombre;

    private String apellido;

    private String contacto;

    private Date fechaNacimiento;

    private Integer descuentoCuota;

    private String categoria;

    @ManyToMany(mappedBy = "socio")
    private List<Cuota> cuotas;
}
